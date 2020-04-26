package com.example.restaurantsfinder.state

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.base.SingleLiveData
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.core.Success
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.example.restaurantsfinder.helper.BreweryMapper
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweriesByStateViewModel(
    private val breweryRepository: BreweryRepository,
    private val breweryMapper: BreweryMapper
) : BaseViewModel() {

    val mutableStateName = ObservableField<String>("")
    val mutableFailureError = SingleLiveData<Any>()
    private val _mutableBreweriesByState = MutableLiveData<List<Brewery>>()
    val mutableBreweriesByState: LiveData<List<Brewery>>
        get() = _mutableBreweriesByState

    fun onDoneClicked() {
        Logger.d("On done button is clicked")
        loadBreweriesByState()
    }

    private fun loadBreweriesByState() {
        val jobId = launch {
            val result = breweryRepository.getBreweriesByState(
                mutableStateName.get() ?: return@launch
            )
            withContext(Dispatchers.Main) {
                result.either(
                    { failure -> onRemoteBreweriesByStateLoaded(failure, null) },
                    { list: List<Brewery> -> onRemoteBreweriesByStateLoaded(null, list) }
                )
            }
        }
        addJob(jobId)
    }

    private fun onRemoteBreweriesByStateLoaded(failure: Failure?, breweries: List<Brewery>?) {
        Logger.d("onRemoteBreweriesByCityLoaded throw: $failure - size: ${breweries?.size}")

        failure?.let {
            onRemoteFailure(it)
        }
        breweries?.let {
            onRemoteSuccess(it)
        }
    }

    private fun onRemoteFailure(failure: Failure) {
        Logger.d("Error getting remote breweries by state name, see the failure message: ${failure.message}")
        mutableFailureError.call()
    }

    private fun onRemoteSuccess(list: List<Brewery>) {
        Logger.d("Success getting the list of breweries: ${list.size}")
        _mutableBreweriesByState.value = list
        mutableStateName.set("")
    }

    fun addBreweryToDB(brewery: Brewery) {

        val jobId = launch {
            val entity = breweryMapper.mapModelToEntity(brewery)
            breweryRepository.addBrewery2DB(entity)
            withContext(Dispatchers.Main) {
                Logger.d("Entity was added to db.")
            }
        }

        addJob(jobId)
    }
}