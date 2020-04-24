package com.example.restaurantsfinder.visited

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
import com.example.restaurantsfinder.databinding.FragmentVisitedBinding
import com.example.restaurantsfinder.helper.ACTION_KEY
import com.example.restaurantsfinder.helper.SharedPrefHelper
import org.koin.android.ext.android.inject

class FragmentBreweriesVisited : BaseFragment<BreweryVisitedViewModel>() {

    private val viewModel: BreweryVisitedViewModel by inject()
    private val sharedPrefHelper: SharedPrefHelper by inject()
    private lateinit var binding: FragmentVisitedBinding

    private lateinit var breweryVisitedAdapter: BreweryVisitedAdapter

    private val clickListener = object : BreweryClickListener {
        override fun onBreweryClicked(id: Int, name: String) {
            sharedPrefHelper.saveBreweryId(id)
            findNavController().navigate(
                R.id.navigateFromVisitedToDetails,
                bundleOf(ACTION_KEY to name)
            )
        }
    }

    override fun initViewModel(): BreweryVisitedViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_visited, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        observers()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        breweryVisitedAdapter = BreweryVisitedAdapter(viewModel).apply {
            this.breweryClickListener = clickListener
        }
        binding.rvBreweries.adapter = breweryVisitedAdapter
    }

    private fun observers() {
        viewModel.mutableFailureError.observe(viewLifecycleOwner, Observer {
            shouldShowOrHideEmptyContainer()
        })

        viewModel.mutableVisitedBreweries.observe(viewLifecycleOwner, Observer {
            breweryVisitedAdapter.submitList(it)
        })
    }

    private fun shouldShowOrHideEmptyContainer() {
        binding.rvBreweries.isVisible = false
        binding.clEmptyContainer.isVisible = true
    }

    override fun onDestroy() {
        super.onDestroy()
        breweryVisitedAdapter.removeListener()
    }
}