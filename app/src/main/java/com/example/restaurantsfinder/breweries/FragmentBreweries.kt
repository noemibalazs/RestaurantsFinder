package com.example.restaurantsfinder.breweries

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapter.BreweryAdapter
import com.example.restaurantsfinder.adapter.BreweryClickListener
import com.example.restaurantsfinder.base.BaseFragment
import com.example.restaurantsfinder.databinding.FragmentBreweriesBinding
import org.koin.android.ext.android.inject

class FragmentBreweries : BaseFragment<BreweryViewModel>() {

    private val viewModel: BreweryViewModel by inject()

    private lateinit var binding: FragmentBreweriesBinding
    private lateinit var breweryAdapter: BreweryAdapter

    private val breweryClickListener = object : BreweryClickListener {
        override fun onBreweryClicked(id: Int) {
            viewModel.onBreweryClicked(id)
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
        binding.rvBreweries.setHasFixedSize(true)
    }

    private fun setObservers() {
        viewModel.mutableBreweriesLiveData.observe(viewLifecycleOwner, Observer {
            breweryAdapter.submitList(it)
        })
    }


}