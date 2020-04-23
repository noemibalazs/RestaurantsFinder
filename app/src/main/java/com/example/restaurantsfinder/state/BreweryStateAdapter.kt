package com.example.restaurantsfinder.state

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.adapterhelper.DifUtilCallback
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBaseStateBinding

class BreweryStateAdapter(
    private val breweriesByStateViewModel: BreweriesByStateViewModel
) : ListAdapter<Brewery, BreweryStateVH>(DifUtilCallback()) {

    var breweryClickListener: BreweryClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BreweryStateVH {
        val binding: ViewItemBaseStateBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context), R.layout.view_item_base_state, parent, false
        )
        return BreweryStateVH(binding, breweriesByStateViewModel, breweryClickListener)
    }

    override fun onBindViewHolder(holder: BreweryStateVH, position: Int) {
        holder.onBind(getItem(position))
    }

    fun removeListener() {
        breweryClickListener = null
    }
}