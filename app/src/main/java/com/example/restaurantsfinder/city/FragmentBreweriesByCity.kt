package com.example.restaurantsfinder.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.base.BaseFragment
import com.example.restaurantsfinder.databinding.FragmentByCityBinding
import com.example.restaurantsfinder.helper.ACTION_KEY
import com.example.restaurantsfinder.helper.SharedPrefHelper
import org.koin.android.ext.android.inject

class FragmentBreweriesByCity : BaseFragment<BreweriesByCityViewModel>() {

    private lateinit var binding: FragmentByCityBinding

    private val viewModel: BreweriesByCityViewModel by inject()
    private val sharedPrefHelper: SharedPrefHelper by inject()
    private lateinit var breweryCityAdapter: BreweryCityAdapter

    private val clickListener = object : BreweryClickListener {

        override fun onBreweryClicked(id: Int, name: String) {
            sharedPrefHelper.saveBreweryId(id)
            findNavController().navigate(
                R.id.navigateFromCityToDetails,
                bundleOf(ACTION_KEY to name)
            )
        }
    }

    override fun initViewModel(): BreweriesByCityViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_by_city, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setObserver()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        breweryCityAdapter = BreweryCityAdapter(viewModel).apply {
            this.breweryClickListener = clickListener
        }
        binding.rvBreweries.adapter = breweryCityAdapter
        binding.rvBreweries.setHasFixedSize(true)
    }

    private fun setObserver() {

        viewModel.mutableBreweryCityList.observe(viewLifecycleOwner, Observer {
            breweryCityAdapter.submitList(it)
        })

        viewModel.mutableFailureError.observe(viewLifecycleOwner, Observer {
            shouldShowOrHideEmptyContainer()
        })
    }

    private fun shouldShowOrHideEmptyContainer() {
        binding.rvBreweries.isVisible = false
        binding.clEmptyContainer.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        breweryCityAdapter.removeListener()
    }
}