package com.example.restaurantsfinder.base

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

import androidx.fragment.app.Fragment
import com.example.restaurantsfinder.R

abstract class BaseFragment<VM: BaseViewModel> : Fragment() {

    private var viewModel: VM ?= null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = initViewModel()
    }

    protected abstract fun initViewModel(): VM

    protected fun setUpToolbar(toolbar: Toolbar, @StringRes title: Int){
        (activity as AppCompatActivity?)?.let {
            it.setSupportActionBar(toolbar)
            it.supportActionBar.let {
                it?.setDisplayHomeAsUpEnabled(true)
                it?.setHomeAsUpIndicator(R.drawable.ic_back)
                it?.title = getString(title)
            }
        }
    }
}