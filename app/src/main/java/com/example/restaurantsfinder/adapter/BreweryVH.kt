package com.example.restaurantsfinder.adapter

import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.breweries.BreweryViewModel
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBreweryBinding

class BreweryVH(
    private val binding: ViewItemBreweryBinding,
    private val breweryViewModel: BreweryViewModel,
    private val breweryClickListener: BreweryClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(brewery: Brewery) {

        binding.viewModel = breweryViewModel
        binding.apply {
            val context = binding.root.context
            tvName.text = context.getString(R.string.name, brewery.name)
            tvStreet.text = context.getString(R.string.street, brewery.street)
            tvState.text = context.getString(R.string.city_state, brewery.city, brewery.state)
            tvSite.text = brewery.website_url

            clContainer.setOnClickListener {
                breweryClickListener?.onBreweryClicked(brewery.id)
            }
        }

    }

}