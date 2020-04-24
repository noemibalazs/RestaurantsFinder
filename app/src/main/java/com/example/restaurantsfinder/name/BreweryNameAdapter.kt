package com.example.restaurantsfinder.name

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.adapterhelper.DifUtilCallback
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBaseNameBinding

class BreweryNameAdapter(
    private val breweriesByNameViewModel: BreweriesByNameViewModel
) : ListAdapter<Brewery, BreweryByNameVH>(DifUtilCallback()) {

    var breweryClickListener: BreweryClickListener? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryByNameVH {
        val binding: ViewItemBaseNameBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.view_item_base_name,
            parent, false
        )
        return BreweryByNameVH(binding, breweriesByNameViewModel, breweryClickListener)
    }

    override fun onBindViewHolder(holder: BreweryByNameVH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun removeListener() {
        breweryClickListener = null
    }
}