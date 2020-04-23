package com.example.restaurantsfinder.city

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.adapterhelper.DifUtilCallback
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBaseCityBinding

class BreweryCityAdapter(
    private val breweriesByCityViewModel: BreweriesByCityViewModel
) : ListAdapter<Brewery, BreweryCityVH>(DifUtilCallback()) {

    var breweryClickListener: BreweryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryCityVH {
        val binding: ViewItemBaseCityBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.view_item_base_city,
            parent,
            false
        )
        return BreweryCityVH(binding, breweriesByCityViewModel, breweryClickListener)
    }

    override fun onBindViewHolder(holder: BreweryCityVH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun removeListener() {
        breweryClickListener = null
    }

}