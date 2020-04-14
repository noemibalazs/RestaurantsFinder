package com.example.restaurantsfinder.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.breweryviewmodel.BreweryViewModel
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBreweryBinding

class BreweryAdapter(
    private val breweryViewModel: BreweryViewModel
) : ListAdapter<Brewery, BreweryVH>(DifUtilCallback()) {

    var breweryListener: BreweryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryVH {
        val binding: ViewItemBreweryBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_item_brewery,
            parent,
            false
        )
        return BreweryVH(binding, breweryViewModel, breweryListener)
    }

    override fun onBindViewHolder(holder: BreweryVH, position: Int) {
        holder.onBind(getItem(position))
    }
}