package com.example.restaurantsfinder.datasource

import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery

interface BreweryRemoteDataSource {

    fun getListOfBreweries(page: Int): Either<Failure, List<Brewery>>

    fun getBreweriesByCity(page: Int, city: String): Either<Failure, List<Brewery>>

    fun getBreweriesByName(page: Int, name: String): Either<Failure, List<Brewery>>

    fun getBreweriesByState(page: Int, state: String): Either<Failure, List<Brewery>>

}