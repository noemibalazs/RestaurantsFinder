package com.example.restaurantsfinder.breweries

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.base.SingleLiveData
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.core.Success
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.example.restaurantsfinder.helper.BreweryMapper
import com.example.restaurantsfinder.helper.OnEvent
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweryViewModel(
    val breweryRepository: BreweryRepository,
    val breweryMapper: BreweryMapper
) : BaseViewModel() {

    private val _mutableBreweriesLiveData = MutableLiveData<List<Brewery>>()
    val mutableBreweriesLiveData: LiveData<List<Brewery>>
        get() = _mutableBreweriesLiveData
    val mutableFailureError = SingleLiveData<Any>()

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
        mutableFailureError.call()
    }

    fun addBreweryToDB(brewery: Brewery) {
        val jobId = launch {
            val entity = breweryMapper.mapModelToEntity(brewery)
            val result = breweryRepository.breweryLocalDataSource.addBrewery(entity)
            withContext(Dispatchers.Main) {
                result.either(
                    { failure -> onLocalBrewerySaved(failure, null) },
                    { success -> onLocalBrewerySaved(null, success) })
            }
        }
        addJob(jobId)
    }

    private fun onLocalBrewerySaved(failure: Failure?, success: Success?) {
        Logger.d("onLocalBrewerySavedResponse throw: $failure - success: $success")

        failure?.let {
            Logger.e("Error saving brewery on local data source, see the failure message: ${failure.message}")
        }

        success?.let {
            Logger.d("Success saving brewery on local data source!")
        }
    }
}