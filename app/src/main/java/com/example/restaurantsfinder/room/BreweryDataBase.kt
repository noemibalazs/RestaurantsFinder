package com.example.restaurantsfinder.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.restaurantsfinder.data.BreweryEntity

@Database(entities = [BreweryEntity::class], exportSchema = false, version = 1)
abstract class BreweryDataBase : RoomDatabase() {

    abstract fun getBreweryDao(): BreweryDAO
}