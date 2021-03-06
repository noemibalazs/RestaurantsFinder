package com.example.restaurantsfinder.di

import com.example.restaurantsfinder.breweries.BreweryViewModel
import com.example.restaurantsfinder.city.BreweriesByCityViewModel
import com.example.restaurantsfinder.datasource.*
import com.example.restaurantsfinder.details.BreweryDetailsViewModel
import com.example.restaurantsfinder.helper.BreweryMapper
import com.example.restaurantsfinder.helper.SharedPrefHelper
import com.example.restaurantsfinder.name.BreweriesByNameViewModel
import com.example.restaurantsfinder.network.BreweryApiService
import com.example.restaurantsfinder.room.BreweryDAO
import com.example.restaurantsfinder.state.BreweriesByStateViewModel
import com.example.restaurantsfinder.visited.BreweryVisitedViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.get
import org.koin.dsl.module

val breweryDataBase = module {
    single { BreweryDAO.getDataBaseInstance(androidApplication().applicationContext) }
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
            breweryRepository = get(),
            breweryMapper = get()
        )
    }
}

val sharedModule = module {
    single { SharedPrefHelper(androidApplication().applicationContext) }
}

val breweryDetailsModule = module {
    viewModel {
        BreweryDetailsViewModel(
            breweryRepository = get(),
            sharedPrefHelper = get(),
            breweryMapper = get()
        )
    }
}

val breweriesByCityViewModule = module {
    viewModel {
        BreweriesByCityViewModel(
            breweryRepository = get(),
            breweryMapper = get()
        )
    }
}

val breweriesByStateViewModule = module {

    viewModel {
        BreweriesByStateViewModel(
            breweryRepository = get(),
            breweryMapper = get()
        )
    }
}

val breweriesByNameModule = module {
    viewModel {
        BreweriesByNameViewModel(
            breweryRepository = get(),
            breweryMapper = get()
        )
    }
}

val breweriesVisitedViewModule = module {
    viewModel {
        BreweryVisitedViewModel(
            breweryRepository = get()
        )
    }
}