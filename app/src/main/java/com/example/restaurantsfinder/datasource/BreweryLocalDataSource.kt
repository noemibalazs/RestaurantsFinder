package com.example.restaurantsfinder.datasource

import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.core.Success
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.data.BreweryEntity

interface BreweryLocalDataSource {

    fun getAllBreweries(): Either<Failure, List<Brewery>>

    fun getBreweryById(id: Int): Brewery

    fun addBrewery(entity: BreweryEntity)

    fun deleteBrewery(entity: BreweryEntity)
}