package com.example.restaurantsfinder.city

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.adapterhelper.BreweryClickListener
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.ViewItemBaseCityBinding
import com.example.restaurantsfinder.helper.DebounceClickListener
import com.orhanobut.logger.Logger

class BreweryCityVH(
    private val binding: ViewItemBaseCityBinding,
    private val breweriesByCityViewModel: BreweriesByCityViewModel,
    private val breweryClickListener: BreweryClickListener?
) : RecyclerView.ViewHolder(binding.root) {

    fun onBind(brewery: Brewery) {

        binding.viewModel = breweriesByCityViewModel
        binding.apply {
            val context = binding.root.context
            tvName.text = context.getString(R.string.name, brewery.name)
            tvStreet.text = context.getString(R.string.street, brewery.street)
            tvState.text = context.getString(R.string.city_state, brewery.city, brewery.state)
            tvSite.text = brewery.website_url

            clContainer.setOnClickListener(object : DebounceClickListener() {
                override fun onDebounce(view: View) {
                    breweryClickListener?.onBreweryClicked(brewery.id, brewery.name)
                    breweriesByCityViewModel.addBreweryToDB(brewery)
                    Logger.d("The site of brewery is: ${brewery.website_url}")
                }
            })
        }
    }
}