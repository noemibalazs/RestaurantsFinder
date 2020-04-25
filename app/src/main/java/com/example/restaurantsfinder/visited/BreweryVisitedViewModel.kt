package com.example.restaurantsfinder.visited

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.base.SingleLiveData
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweryVisitedViewModel(
    private val breweryRepository: BreweryRepository
) : BaseViewModel() {

    val mutableFailureError = SingleLiveData<Any>()
    private var _mutableVisitedBreweries = MutableLiveData<List<Brewery>>()
    val mutableVisitedBreweries: LiveData<List<Brewery>>
        get() = _mutableVisitedBreweries

    init {
        loadVisitedBreweries()
    }

    private fun loadVisitedBreweries() {

        val jobId = launch {
            val result = breweryRepository.getAllBreweriesFromDB()
            withContext(Dispatchers.Main) {
                result.either(
                    { failure -> onLocaleLoadedBreweriesResponse(failure, null) },
                    { list: List<Brewery> -> onLocaleLoadedBreweriesResponse(null, list) }
                )
            }
        }
        addJob(jobId)
    }

    private fun onLocaleLoadedBreweriesResponse(failure: Failure?, list: List<Brewery>?) {
        Logger.d("On locale loaded breweries response, throw : $failure - list: $list")

        failure?.let {
            onLocaleFailure(it)
        }

        list?.let {
            onLocaleSuccess(it)
        }
    }

    private fun onLocaleFailure(failure: Failure) {
        Logger.d("Error getting list of visited breweries, see failure message: ${failure.message}")
        mutableFailureError.call()
    }

    private fun onLocaleSuccess(list: List<Brewery>) {
        Logger.d("Success getting list of visited breweries!")
        _mutableVisitedBreweries.value = list
    }
}