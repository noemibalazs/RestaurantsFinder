package com.example.restaurantsfinder.breweryviewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweryViewModel(
    val breweryRepository: BreweryRepository
) : BaseViewModel() {

    private val _mutableBreweriesLiveData = MutableLiveData<List<Brewery>>()
    val mutableBreweriesLiveData: LiveData<List<Brewery>>
        get() = _mutableBreweriesLiveData

    init {
        this.loadBreweriesFromServer()
    }

    protected fun loadBreweriesFromServer() {

        val jobId = launch {
            val response = breweryRepository.getAllBreweriesFromServer()
            withContext(Dispatchers.Main) {
                response.either(
                    { failure -> onRemoteBreweriesLoaded(failure, null) },
                    { list -> onRemoteBreweriesLoaded(null, list) })
            }
        }

        addJob(jobId)
    }

    private fun onRemoteBreweriesLoaded(failure: Failure?, breweries: List<Brewery>?) {

        Logger.d("onRemoteBreweriesLoaded throw: $failure - size: ${breweries?.size}")

        failure?.let {
            onRemoteFailure(it)
        }
        breweries?.let {
            _mutableBreweriesLiveData.value = it
        }
    }

    private fun onRemoteFailure(failure: Failure) {
        Logger.e("Error getting remote breweries, see the failure message: ${failure.message}")
    }
}