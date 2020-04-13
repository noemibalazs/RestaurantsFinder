package com.example.restaurantsfinder.datasource

import com.example.restaurantsfinder.data.BreweryEntity

class BreweryRepository(
    val breweryRemoteDataSource: BreweryRemoteDataSource,
    val breweryLocalDataSource: BreweryLocalDataSource
) {

    fun getAllBreweriesFromServer(page: Int) = breweryRemoteDataSource.getListOfBreweries(page)

    fun getBreweriesByName(page: Int, name: String) =
        breweryRemoteDataSource.getBreweriesByName(page, name)

    fun getBreweriesByCity(page: Int, city: String) =
        breweryRemoteDataSource.getBreweriesByName(page, city)

    fun getBreweriesByState(page: Int, state: String) =
        breweryRemoteDataSource.getBreweriesByName(page, state)

    fun getAllBreweriesFromDB() = breweryLocalDataSource.getAllBreweries()

    fun getBreweryById(id: Int) = breweryLocalDataSource.getBreweryById(id)

    fun addBrewery2DB(entity: BreweryEntity) = breweryLocalDataSource.addBrewery(entity)

    fun deleteBreweryFromDB(entity: BreweryEntity) = breweryLocalDataSource.deleteBrewery(entity)
}