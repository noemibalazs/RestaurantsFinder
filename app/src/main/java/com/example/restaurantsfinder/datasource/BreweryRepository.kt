package com.example.restaurantsfinder.datasource

import com.example.restaurantsfinder.data.BreweryEntity

class BreweryRepository(
    val breweryRemoteDataSource: BreweryRemoteDataSource,
    val breweryLocalDataSource: BreweryLocalDataSource
) {

    fun getAllBreweriesFromServer() = breweryRemoteDataSource.getListOfBreweries()

    fun getBreweriesByName(name: String) =
        breweryRemoteDataSource.getBreweriesByName(name)

    fun getBreweriesByCity(city: String) =
        breweryRemoteDataSource.getBreweriesByName(city)

    fun getBreweriesByState(state: String) =
        breweryRemoteDataSource.getBreweriesByName(state)

    fun getBreweryById(id: Int) = breweryRemoteDataSource.getBreweryById(id)

    fun getAllBreweriesFromDB() = breweryLocalDataSource.getAllBreweries()

    fun getBreweryByIdFromDB(id: Int) = breweryLocalDataSource.getBreweryById(id)

    fun addBrewery2DB(entity: BreweryEntity) = breweryLocalDataSource.addBrewery(entity)

    fun deleteBreweryFromDB(entity: BreweryEntity) = breweryLocalDataSource.deleteBrewery(entity)
}