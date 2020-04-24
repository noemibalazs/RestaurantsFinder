package com.example.restaurantsfinder.visited

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.adapterhelper.DifUtilCallback
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemVisitedBinding

class BreweryVisitedAdapter(
    private val breweryVisitedViewModel: BreweryVisitedViewModel
) : ListAdapter<Brewery, BreweryVisitedVH>(DifUtilCallback()) {

    var breweryClickListener: BreweryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryVisitedVH {
        val binding: ViewItemVisitedBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_item_visited,
            parent,
            false
        )
        return BreweryVisitedVH(binding, breweryVisitedViewModel, breweryClickListener)

    }

    override fun onBindViewHolder(holder: BreweryVisitedVH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun removeListener() {
        breweryClickListener = null
    }

}