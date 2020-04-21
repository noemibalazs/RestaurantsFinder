package com.example.restaurantsfinder.city

import android.view.View
import com.example.restaurantsfinder.base.BaseAdapter
import com.example.restaurantsfinder.base.BaseVH
import com.example.restaurantsfinder.helper.DebounceClickListener

class BreweryCityAdapter(
    private val breweriesByCityViewModel: BreweriesByCityViewModel
) : BaseAdapter(breweriesByCityViewModel) {

    override fun removeListener() {
        this.breweryListener = null
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        holder.binding.clContainer.setOnClickListener(object : DebounceClickListener() {
            override fun onDebounce(view: View) {
                breweriesByCityViewModel.addBreweryToDB(getItem(position))
            }
        })
    }
}