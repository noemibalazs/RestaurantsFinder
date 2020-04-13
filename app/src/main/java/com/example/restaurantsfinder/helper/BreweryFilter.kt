package com.example.restaurantsfinder.helper

enum class BreweryFilter(private var filter: String) {
    BY_NAME("by_name"),
    BY_STATE("by_state"),
    BY_CITY("by_city"),
    BY_PAGE("page"),
    BY_PER_PAGE("per_page");

    fun getFilter(): String = filter
}