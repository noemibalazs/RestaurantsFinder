package com.example.restaurantsfinder.helper

import android.content.Context

class SharedPrefHelper(private val context: Context) {

    private var shared = context.getSharedPreferences("My helper", Context.MODE_PRIVATE)

    fun saveBreweryId(id: Int) {
        shared.edit().putInt(BREWERY_KEY, id).apply()
    }

    fun getBreweryId(): Int = shared.getInt(BREWERY_KEY, 0)
}