package com.example.restaurantsfinder.base

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapter.BreweryClickListener
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBaseBinding
import com.example.restaurantsfinder.helper.DebounceClickListener

class BaseVH(
    val binding: ViewItemBaseBinding,
    private val baseViewModel: BaseViewModel,
    private val breweryClickListener: BreweryClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(brewery: Brewery) {
        binding.viewModel = baseViewModel
        binding.apply {
            val context = binding.root.context
            tvName.text = context.getString(R.string.name, brewery.name)
            tvStreet.text = context.getString(R.string.street, brewery.street)
            tvState.text = context.getString(R.string.city_state, brewery.city, brewery.state)
            tvSite.text = brewery.website_url

            clContainer.setOnClickListener(object : DebounceClickListener() {
                override fun onDebounce(view: View) {
                    breweryClickListener?.onBreweryClicked(brewery.id, brewery.name)
                }
            })
        }
    }
}