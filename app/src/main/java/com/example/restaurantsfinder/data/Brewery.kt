package com.example.restaurantsfinder.data

import com.google.gson.annotations.SerializedName

data class Brewery(
    @field:SerializedName("id") val id: Int,
    @field:SerializedName("name") val name: String,
    @field:SerializedName("brewery_type") val breweryType: String,
    @field:SerializedName("street") val street: String,
    @field:SerializedName("city") val city: String,
    @field:SerializedName("state") val state: String,
    @field:SerializedName("country") val country: String,
    @field:SerializedName("longitude") val longitude: String,
    @field:SerializedName("latitude") val latitude: String,
    @field:SerializedName("phone") val phone: String,
    @field:SerializedName("website_url") val site: String,
    @field:SerializedName("updated_at") val updated: String
)