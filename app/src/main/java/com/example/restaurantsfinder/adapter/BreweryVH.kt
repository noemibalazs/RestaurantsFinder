package com.example.restaurantsfinder.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.breweries.BreweryViewModel
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBreweryBinding
import com.example.restaurantsfinder.helper.DebounceClickListener

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

            clContainer.setOnClickListener(object : DebounceClickListener() {
                override fun onDebounce(view: View) {
                    breweryClickListener?.onBreweryClicked(brewery.id, brewery.name)
                    breweryViewModel.addBreweryToDB(brewery)
                }
            })
        }
    }
}