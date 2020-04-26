package com.example.restaurantsfinder.city

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.base.SingleLiveData
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.core.Success
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.data.BreweryEntity
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.example.restaurantsfinder.helper.BreweryMapper
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweriesByCityViewModel(
    val breweryRepository: BreweryRepository,
    val breweryMapper: BreweryMapper
) : BaseViewModel() {

    val mutableCityName = ObservableField<String>("")
    val mutableFailureError = SingleLiveData<Any>()
    private var _mutableBreweryCityList = MutableLiveData<List<Brewery>>()
    val mutableBreweryCityList: LiveData<List<Brewery>>
        get() = _mutableBreweryCityList


    fun onDoneClicked() {
        Logger.d("On done button is clicked")
        loadBreweriesByCity()
    }

    private fun loadBreweriesByCity() {
        val jobId = launch {
            val result = breweryRepository.getBreweriesByCity(
                mutableCityName.get() ?: return@launch
            )
            withContext(Dispatchers.Main) {
                result.either(
                    { failure -> onRemoteBreweriesByCityLoaded(failure, null) },
                    { list: List<Brewery> -> onRemoteBreweriesByCityLoaded(null, list) }
                )
            }
        }

        addJob(jobId)
    }

    private fun onRemoteBreweriesByCityLoaded(failure: Failure?, breweries: List<Brewery>?) {
        Logger.d("onRemoteBreweriesByCityLoaded throw: $failure - size: ${breweries?.size}")

        failure?.let {
            onRemoteFailure(it)
        }
        breweries?.let {
            onRemoteSuccess(it)
        }
    }

    private fun onRemoteFailure(failure: Failure) {
        Logger.d("Error getting remote breweries by city name, see the failure message: ${failure.message}")
        mutableFailureError.call()
    }

    private fun onRemoteSuccess(list: List<Brewery>) {
        Logger.d("Success getting the list of breweries: ${list.size}")
        _mutableBreweryCityList.value = list
        mutableCityName.set("")
    }

    fun addBreweryToDB(brewery: Brewery) {

        val jobId = launch {
            val entity: BreweryEntity = breweryMapper.mapModelToEntity(brewery)
            breweryRepository.addBrewery2DB(entity)
            withContext(Dispatchers.Main) {
                Logger.d("Entity was added to the database.")
            }
        }
        addJob(jobId)
    }
}