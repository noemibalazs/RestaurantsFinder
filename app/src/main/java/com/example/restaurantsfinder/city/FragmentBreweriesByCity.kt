package com.example.restaurantsfinder.city

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapter.BreweryAdapter
import com.example.restaurantsfinder.adapter.BreweryClickListener
import com.example.restaurantsfinder.base.BaseFragment
import com.example.restaurantsfinder.databinding.FragmentByBinding
import org.koin.android.ext.android.inject

class FragmentBreweriesByCity : BaseFragment<BreweriesByCityViewModel>() {

    private lateinit var binding: FragmentByBinding

    private val viewModel: BreweriesByCityViewModel by inject()
    private lateinit var adapter: BreweryAdapter

    private val breweryClickListener = object: BreweryClickListener{

        override fun onBreweryClicked(id: Int, name: String) {
            TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }

    override fun initViewModel(): BreweriesByCityViewModel {
        return viewModel
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_by, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        setObserver()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
    }

    private fun setObserver(){

    }
}