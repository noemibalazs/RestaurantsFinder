package com.example.restaurantsfinder.helper

import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.data.BreweryEntity

class BreweryMapper {

    fun mapModelToEntity(model: Brewery): BreweryEntity {
        return BreweryEntity(
            id = model.id,
            name = model.name,
            brewery_type = model.brewery_type,
            street = model.street,
            city = model.city,
            state = model.state,
            country = model.country,
            longitude = model.longitude,
            latitude = model.latitude,
            phone = model.phone,
            site = model.website_url,
            updated = model.updated_at
        )
    }

    fun mapEntityToModel(entity: BreweryEntity): Brewery {
        return Brewery(
            id = entity.id,
            name = entity.name,
            brewery_type = entity.brewery_type,
            street = entity.street,
            city = entity.city,
            state = entity.state,
            country = entity.country,
            longitude = entity.longitude,
            latitude = entity.latitude,
            phone = entity.phone,
            website_url = entity.site,
            updated_at = entity.updated
        )
    }
}