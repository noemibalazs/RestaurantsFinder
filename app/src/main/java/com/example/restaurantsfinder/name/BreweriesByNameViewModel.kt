package com.example.restaurantsfinder.name

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

class BreweriesByNameViewModel(
    private val breweryRepository: BreweryRepository,
    private val breweryMapper: BreweryMapper
) : BaseViewModel() {

    val mutableBreweryName = ObservableField<String>("")
    val mutableFailureError = SingleLiveData<Any>()
    private val _mutableBreweriesByName = MutableLiveData<List<Brewery>>()
    val mutableBreweriesByName: LiveData<List<Brewery>>
        get() = _mutableBreweriesByName

    fun onDoneClicked() {
        Logger.d("On done button clicked.")
        loadBreweriesByName()
    }

    private fun loadBreweriesByName() {

        val jobId = launch {
            val result = breweryRepository.getBreweriesByName(
                mutableBreweryName.get() ?: return@launch
            )
            withContext(Dispatchers.Main) {
                result.either(
                    { failure -> onRemoteBreweriesByNameLoaded(failure, null) },
                    { list: List<Brewery> -> onRemoteBreweriesByNameLoaded(null, list) }
                )
            }
        }

        addJob(jobId)
    }

    private fun onRemoteBreweriesByNameLoaded(failure: Failure?, list: List<Brewery>?) {
        Logger.d("On remote breweries loaded by name, throw failure: $failure - see list: $list")

        failure?.let {
            onRemoteFailure(it)
        }

        list?.let {
            onRemoteSuccess(it)
        }

    }

    private fun onRemoteFailure(failure: Failure) {
        Logger.d("Error getting list of breweries by name, see error message: ${failure.message}")
        mutableFailureError.call()
    }

    private fun onRemoteSuccess(list: List<Brewery>) {
        Logger.d("Success getting the list of breweries, see the size of list: ${list.size}")
        _mutableBreweriesByName.value = list
    }

    fun addBreweryToDB(brewery: Brewery) {

        val jobId = launch {
            val entity = breweryMapper.mapModelToEntity(brewery)
            breweryRepository.breweryLocalDataSource.addBrewery(entity)
            withContext(Dispatchers.Main) {
                Logger.d("Entity was added to db.")
            }
        }

        addJob(jobId)
    }


    private fun onLocaleFailure(failure: Failure) {
        Logger.e("Error saving entity into the database, see message: ${failure.message}")
    }

    private fun onLocaleSuccess(success: Success) {
        Logger.d("Success saving entity!")
    }
}