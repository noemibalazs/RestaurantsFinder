package com.example.restaurantsfinder.helper

import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.data.BreweryEntity

class BreweryMapper {

    fun mapModelToEntity(model: Brewery): BreweryEntity {
        return BreweryEntity(
            id = model.id,
            name = model.name,
            breweryType = model.breweryType,
            street = model.street,
            city = model.city,
            state = model.state,
            country = model.country,
            longitude = model.longitude,
            latitude = model.latitude,
            phone = model.phone,
            site = model.site,
            updated = model.updated
        )
    }

    fun mapEntityToModel(entity: BreweryEntity): Brewery {
        return Brewery(
            id = entity.id,
            name = entity.name,
            breweryType = entity.breweryType,
            street = entity.street,
            city = entity.city,
            state = entity.state,
            country = entity.country,
            longitude = entity.longitude,
            latitude = entity.latitude,
            phone = entity.phone,
            site = entity.site,
            updated = entity.updated
        )
    }
}