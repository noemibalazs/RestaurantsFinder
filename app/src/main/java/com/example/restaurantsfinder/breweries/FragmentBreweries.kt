package com.example.restaurantsfinder.breweries

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
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.FragmentBreweriesBinding
import com.example.restaurantsfinder.helper.ACTION_KEY
import com.example.restaurantsfinder.helper.SharedPrefHelper
import org.koin.android.ext.android.inject

class FragmentBreweries : BaseFragment<BreweryViewModel>() {

    private val viewModel: BreweryViewModel by inject()
    private val sharedPrefHelper: SharedPrefHelper by inject()

    private lateinit var binding: FragmentBreweriesBinding
    private lateinit var breweryAdapter: BreweryAdapter

    private val breweryClickListener = object : BreweryClickListener {
        override fun onBreweryClicked(id: Int, name: String) {
            sharedPrefHelper.saveBreweryId(id)
            findNavController().navigate(
                R.id.navigateFromBreweriesToDetails,
                bundleOf(ACTION_KEY to name)
            )
        }

        override fun addBreweryToDB(brewery: Brewery) {
            viewModel.addBreweryToDB(brewery)
        }
    }

    override fun initViewModel(): BreweryViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_breweries, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setObservers()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        breweryAdapter = BreweryAdapter(viewModel).apply {
            this.breweryListener = breweryClickListener
        }
        binding.rvBreweries.adapter = breweryAdapter
    }

    private fun setObservers() {
        viewModel.mutableBreweriesLiveData.observe(viewLifecycleOwner, Observer {
            breweryAdapter.submitList(it)
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
        breweryAdapter.removeListener()
    }
}