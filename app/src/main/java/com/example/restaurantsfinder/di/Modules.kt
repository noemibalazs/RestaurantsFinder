package com.example.restaurantsfinder.di

import com.example.restaurantsfinder.breweries.BreweryViewModel
import com.example.restaurantsfinder.datasource.*
import com.example.restaurantsfinder.helper.BreweryMapper
import com.example.restaurantsfinder.helper.SharedPrefHelper
import com.example.restaurantsfinder.network.BreweryApiService
import com.example.restaurantsfinder.room.BreweryDAO
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val breweryDataBase = module {
    single { BreweryDAO.getDataBaseInstance(androidApplication()) }
}

val networkModule = module {
    single { BreweryApiService.getRetrofitInstance() }
}

val breweryModule = module {

    factory {
        BreweryMapper()
    }

    single<BreweryLocalDataSource> {
        BreweryLocalDataSourceImpl(
            breweryDAO = get(),
            breweryMapper = get()
        )
    }

    single<BreweryRemoteDataSource> {
        BreweryRemoteDataSourceImpl(
            breweryApiService = get()
        )
    }

    single {
        BreweryRepository(
            breweryLocalDataSource = get(),
            breweryRemoteDataSource = get()
        )
    }
}

val breweryViewModel = module {
    viewModel {
        BreweryViewModel(
            breweryRepository = get()
        )
    }
}

val sharedModule = module {
    single { SharedPrefHelper(androidApplication().applicationContext) }
}