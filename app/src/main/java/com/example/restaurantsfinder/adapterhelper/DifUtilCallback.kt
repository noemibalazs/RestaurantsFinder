package com.example.restaurantsfinder.adapterhelper

import androidx.recyclerview.widget.DiffUtil
import com.example.restaurantsfinder.data.Brewery

class DifUtilCallback : DiffUtil.ItemCallback<Brewery>() {

    override fun areItemsTheSame(oldItem: Brewery, newItem: Brewery): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Brewery, newItem: Brewery): Boolean {
        return oldItem == newItem
    }
}