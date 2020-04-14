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

    override fun getListOfBreweries(): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getListOfBreweries().executeAndDeliver().map { it }
        }
    }

    override fun getBreweriesByCity(city: String): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getBreweriesByCity(city).executeAndDeliver().map { it }
        }
    }

    override fun getBreweriesByName(name: String): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getBreweriesByName(name).executeAndDeliver().map { it }
        }
    }

    override fun getBreweriesByState(state: String): Either<Failure, List<Brewery>> {
        return breweryApiService.let {
            breweryApiService.getBreweriesByState(state).executeAndDeliver().map {
                it
            }
        }
    }

    override fun getBreweryById(id: Int): Either<Failure, Brewery> {
        return breweryApiService.let {
            breweryApiService.getBreweryById(id).executeAndDeliver().map {
                it
            }
        }
    }
}