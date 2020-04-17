package com.example.restaurantsfinder.helper

data class BreweryGPS(
    val lat: Double,
    val lng: Double
) {

    override fun toString(): String {
        return "BreweryGPS(lat=$lat, lng=$lng)"
    }
}