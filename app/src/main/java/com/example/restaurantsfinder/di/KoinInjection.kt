package com.example.restaurantsfinder.di

import org.koin.core.module.Module

class KoinInjection {

    companion object {

        fun getModules(): MutableList<Module> {

            fun getNetworkModule() = listOf(networkModule)

            fun getDataBaseModule() = listOf(breweryDataBase)

            fun getBreweryModule() = listOf(breweryModule)

            fun getBreweryVM() = listOf(breweryViewModel)

            fun getSharedPrefModule() = listOf(sharedModule)

            fun getBreweryDetails() = listOf(breweryDetailsModule)

            return mutableListOf<Module>().apply {
                addAll(getNetworkModule())
                addAll(getDataBaseModule())
                addAll(getBreweryModule())
                addAll(getBreweryVM())
                addAll(getSharedPrefModule())
                addAll(getBreweryDetails())
            }
        }
    }
}