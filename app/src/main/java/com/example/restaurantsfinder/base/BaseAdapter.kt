package com.example.restaurantsfinder.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapter.BreweryClickListener
import com.example.restaurantsfinder.adapter.DifUtilCallback
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBaseBinding

abstract class BaseAdapter(
    private val baseViewModel: BaseViewModel
) : ListAdapter<Brewery, BaseVH>(DifUtilCallback()){

    var breweryListener: BreweryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        val binding: ViewItemBaseBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.view_item_base, parent, false)
        return BaseVH(binding, baseViewModel, breweryListener)
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
       holder.bind(getItem(position))
    }

    abstract fun removeListener()
}