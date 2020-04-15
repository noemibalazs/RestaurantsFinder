package com.example.restaurantsfinder.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.restaurantsfinder.data.Brewery

class DifUtilCallback : DiffUtil.ItemCallback<Brewery>() {

    override fun areItemsTheSame(oldItem: Brewery, newItem: Brewery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Brewery, newItem: Brewery): Boolean {
        return oldItem.id == newItem.id &&
                oldItem.name == newItem.name &&
                oldItem.city == newItem.city &&
                oldItem.state == newItem.state &&
                oldItem.latitude == newItem.latitude &&
                oldItem.longitude == newItem.longitude &&
                oldItem.website_url == newItem.website_url
    }
}