package com.example.restaurantsfinder.datasource

import android.database.sqlite.SQLiteException
import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.core.Success
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.data.BreweryEntity
import com.example.restaurantsfinder.helper.BreweryMapper
import com.example.restaurantsfinder.room.BreweryDAO
import com.orhanobut.logger.Logger

class BreweryLocalDataSourceImpl(
    private val breweryDAO: BreweryDAO,
    private val breweryMapper: BreweryMapper
) : BreweryLocalDataSource {

    override fun getAllBreweries(): Either<Failure, List<Brewery>> {
        Logger.d("Try to get the entity list from data base.")
        val entityList = breweryDAO.getAllBreweries()
        val breweryList = entityList.map { entity -> breweryMapper.mapEntityToModel(entity) }
        return Either.Right(breweryList)
    }

    override fun getBreweryById(id: Int): Brewery {
        Logger.d("Try to get the entity by id.")
        val entity = breweryDAO.getBrewery(id)
        return breweryMapper.mapEntityToModel(entity)
    }

    override fun addBrewery(entity: BreweryEntity) {
        Logger.d("Try to add entity to data base")
        breweryDAO.addBrewery(entity)
    }

    override fun deleteBrewery(entity: BreweryEntity) {
        Logger.d("Try to delete the entity from data base.")
        breweryDAO.deleteBrewery(entity)
    }
}