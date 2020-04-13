package com.example.restaurantsfinder.datasource

import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.extensions.executeAndDeliver
import com.example.restaurantsfinder.extensions.map
import com.example.restaurantsfinder.network.BreweryApiService

class BreweryRemoteDataSourceImpl(
    private val breweryApiService: BreweryApiService
) : BreweryRemoteDataSource {

    override fun getListOfBreweries(page: Int): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getListOfBreweries(page).executeAndDeliver().map { it }
        }
    }

    override fun getBreweriesByCity(page: Int, city: String): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getBreweriesByCity(page, city).executeAndDeliver().map { it }
        }
    }

    override fun getBreweriesByName(page: Int, name: String): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getBreweriesByName(page, name).executeAndDeliver().map { it }
        }
    }

    override fun getBreweriesByState(page: Int, state: String): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getBreweriesByState(page, state).executeAndDeliver().map { it }
        }
    }
}