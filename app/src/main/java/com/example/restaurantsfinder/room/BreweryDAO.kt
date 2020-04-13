package com.example.restaurantsfinder.room

import android.content.Context
import androidx.room.*
import com.example.restaurantsfinder.data.BreweryEntity
import com.example.restaurantsfinder.helper.BREWERY_DB

@Dao
interface BreweryDAO {

    @Query("SELECT * FROM brewery_table")
    fun getAllBreweries(): MutableList<BreweryEntity>

    @Query("SELECT * FROM brewery_table WHERE id = :id")
    fun getBrewery(id: Int): BreweryEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addBrewery(entity: BreweryEntity)

    @Delete
    fun deleteBrewery(entity: BreweryEntity)

    companion object {
        fun getDataBaseInstance(context: Context): BreweryDAO {
            return Room.databaseBuilder(context, BreweryDataBase::class.java, BREWERY_DB).build()
                .getBreweryDao()
        }
    }
}