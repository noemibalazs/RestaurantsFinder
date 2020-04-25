package com.example.restaurantsfinder.adapterhelper

import com.example.restaurantsfinder.data.Brewery

interface BreweryClickListener {
    fun onBreweryClicked(id: Int, name:String)

    fun addBreweryToDB(brewery: Brewery)
}