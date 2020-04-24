package com.example.restaurantsfinder.state

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.base.BaseFragment
import com.example.restaurantsfinder.databinding.FragmentByStateBinding
import com.example.restaurantsfinder.helper.ACTION_KEY
import com.example.restaurantsfinder.helper.SharedPrefHelper
import org.koin.android.ext.android.inject

class FragmentBreweriesByState : BaseFragment<BreweriesByStateViewModel>() {

    private val viewModel: BreweriesByStateViewModel by inject()
    private val sharedPrefHelper: SharedPrefHelper by inject()
    private lateinit var binding: FragmentByStateBinding

    private lateinit var breweriesByStateAdapter: BreweryStateAdapter

    private val clickListener = object : BreweryClickListener {
        override fun onBreweryClicked(id: Int, name: String) {
            sharedPrefHelper.saveBreweryId(id)
            findNavController().navigate(
                R.id.navigateFromStateToDetails,
                bundleOf(ACTION_KEY to name)
            )
        }
    }

    override fun initViewModel(): BreweriesByStateViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_by_state, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setObserver()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        breweriesByStateAdapter = BreweryStateAdapter(viewModel).apply {
            this.breweryClickListener = clickListener
        }
        binding.rvBreweries.adapter = breweriesByStateAdapter
    }

    private fun setObserver() {

        viewModel.mutableBreweriesByState.observe(viewLifecycleOwner, Observer {
            breweriesByStateAdapter.submitList(it)
            hideSoftKeyBoard()
        })

        viewModel.mutableFailureError.observe(viewLifecycleOwner, Observer {
            shouldShowOrHideEmptyLayout()
        })
    }


    private fun shouldShowOrHideEmptyLayout() {
        binding.rvBreweries.isVisible = false
        binding.clEmptyContainer.isVisible = true
    }

    private fun hideSoftKeyBoard() {
        val inputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSearchedByState.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        breweriesByStateAdapter.removeListener()
    }
}