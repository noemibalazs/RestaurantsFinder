package com.example.restaurantsfinder.datasource

import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery

interface BreweryRemoteDataSource {

    fun getListOfBreweries(): Either<Failure, List<Brewery>>

    fun getBreweriesByCity(city: String): Either<Failure, List<Brewery>>

    fun getBreweriesByName(name: String): Either<Failure, List<Brewery>>

    fun getBreweriesByState(state: String): Either<Failure, List<Brewery>>

    fun getBreweryById(id: Int): Either<Failure, Brewery>

}