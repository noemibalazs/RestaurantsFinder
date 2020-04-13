package com.example.restaurantsfinder.helper

import android.content.Context

class SharedPrefHelper(private val context: Context) {

    private var shared = context.getSharedPreferences("My helper", Context.MODE_PRIVATE)
}