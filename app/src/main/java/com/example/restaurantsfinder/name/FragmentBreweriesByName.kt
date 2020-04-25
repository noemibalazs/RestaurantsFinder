package com.example.restaurantsfinder.name

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
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.FragmentByNameBinding
import com.example.restaurantsfinder.helper.ACTION_KEY
import com.example.restaurantsfinder.helper.SharedPrefHelper
import org.koin.android.ext.android.inject

class FragmentBreweriesByName : BaseFragment<BreweriesByNameViewModel>() {

    private val viewModel: BreweriesByNameViewModel by inject()
    private val sharedPresentException: SharedPrefHelper by inject()

    private lateinit var binding: FragmentByNameBinding
    private lateinit var breweryNameAdapter: BreweryNameAdapter

    private val clickListener = object : BreweryClickListener {
        override fun onBreweryClicked(id: Int, name: String) {
            sharedPresentException.saveBreweryId(id)
            findNavController().navigate(
                R.id.navigateFromNameToDetails,
                bundleOf(ACTION_KEY to name)
            )
        }

        override fun addBreweryToDB(brewery: Brewery) {
            viewModel.addBreweryToDB(brewery)
        }
    }

    override fun initViewModel(): BreweriesByNameViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_by_name, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setObservers()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        breweryNameAdapter = BreweryNameAdapter(viewModel).apply {
            this.breweryClickListener = clickListener
        }
        binding.rvBreweries.adapter = breweryNameAdapter
    }

    private fun setObservers() {
        viewModel.mutableFailureError.observe(viewLifecycleOwner, Observer {
            shouldShowOrHideEmptyContainer()
        })

        viewModel.mutableBreweriesByName.observe(viewLifecycleOwner, Observer {
            breweryNameAdapter.submitList(it)
            hideSoftKeyBoard()
        })
    }

    private fun shouldShowOrHideEmptyContainer() {
        binding.rvBreweries.isVisible = false
        binding.clEmptyContainer.isVisible = true
    }

    private fun hideSoftKeyBoard() {
        val inputMethodManager =
            activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(binding.etSearchedByName.windowToken, 0)
    }

    override fun onDestroy() {
        super.onDestroy()
        breweryNameAdapter.removeListener()
    }
}