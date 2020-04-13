package com.example.restaurantsfinder.datasource

import android.database.sqlite.SQLiteException
import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.core.Success
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.data.BreweryEntity
import com.example.restaurantsfinder.helper.BreweryMapper
import com.example.restaurantsfinder.room.BreweryDAO

class BreweryLocalDataSourceImpl(
    private val breweryDAO: BreweryDAO,
    private val breweryMapper: BreweryMapper
) : BreweryLocalDataSource {

    override fun getAllBreweries(): Either<Failure, List<Brewery>> = try {
        breweryDAO.getAllBreweries().let { mutableList: MutableList<BreweryEntity> ->
            val breweryModelList = mutableList.map { entity -> breweryMapper.mapEntityToModel(entity) }
            Either.Right(breweryModelList)
        }
        Either.Left(Failure.ResourceNotFoundFailure("The list of entities were not found"))
    } catch (e: SQLiteException) {
        Either.Left(Failure.DataBaseFailure(e.message?:"Data base failure message: ${e.message}"))
    }

    override fun getBreweryById(id: Int): Either<Failure, Brewery> = try {
        breweryDAO.getBrewery(id).let { entity ->
            val breweryModel = breweryMapper.mapEntityToModel(entity)
            Either.Right(breweryModel)
        }
        Either.Left(Failure.ResourceNotFoundFailure("The brewery entity was not found by id: $id"))
    }catch (e: SQLiteException){
        Either.Left(Failure.DataBaseFailure("Data base failure message: ${e.message}"))
    }

    override fun addBrewery(entity: BreweryEntity): Either<Failure, Success> = try {
        breweryDAO.addBrewery(entity).let {
            Either.Right(Success())
        }
        Either.Left(Failure.ResourceNotFoundFailure("Entity was not added to db: $entity"))
    }catch (e: SQLiteException){
        Either.Left(Failure.DataBaseFailure("Data base failure message: ${e.message}"))
    }

    override fun deleteBrewery(entity: BreweryEntity): Either<Failure, Success> = try {
        breweryDAO.deleteBrewery(entity).let {
            Either.Right(Success())
        }
        Either.Left(Failure.ResourceNotFoundFailure("Entity was not deleted: $entity"))
    }catch (e: SQLiteException){
        Either.Left(Failure.DataBaseFailure("Data base failure message: ${e.message}"))
    }


}