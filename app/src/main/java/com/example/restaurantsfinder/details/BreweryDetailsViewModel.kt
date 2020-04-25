package com.example.restaurantsfinder.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.base.SingleLiveData
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.example.restaurantsfinder.helper.BreweryMapper
import com.example.restaurantsfinder.helper.SharedPrefHelper
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweryDetailsViewModel(
    private val breweryRepository: BreweryRepository,
    private val sharedPrefHelper: SharedPrefHelper,
    private val breweryMapper: BreweryMapper
) : BaseViewModel() {

    private var brewerY: Brewery? = null
    private var _mutableBrewery: MutableLiveData<Brewery> = MutableLiveData()
    val mutableBrewery: LiveData<Brewery>
        get() = _mutableBrewery
    val onSiteClicked = SingleLiveData<Any>()
    val onDeleteClicked = SingleLiveData<Any>()
    val deleteClicked = SingleLiveData<Any>()

    init {
        loadBreweryById()
    }

    private fun loadBreweryById() {
        val jobId = launch {
            val result =
                breweryRepository.getBreweryById(sharedPrefHelper.getBreweryId())
            withContext(Dispatchers.Main) {
                result.either(
                    { failure -> onBreweryResponse(failure, null) },
                    { brewery -> onBreweryResponse(null, brewery) }
                )
            }
        }

        addJob(jobId)
    }

    private fun onBreweryResponse(failure: Failure?, brewery: Brewery?) {
        Logger.d("onBreweryResponse throw: $failure brewery: ${brewery?.name}")

        failure?.let {
            Logger.d("onBreweryResponse throw failure, see message: ${it.message}")
        }

        brewery?.let {
            _mutableBrewery.value = it
            brewerY = it
        }
    }

    fun onSiteClicked() {
        Logger.d("To visit the site is clicked")
        onSiteClicked.call()
    }

    fun onDeleteClicked() {
        Logger.d("On delete button is clicked.")
        onDeleteClicked.call()
    }

    fun deleteBreweryEntity(){
        val jobId = launch {
            brewerY?.let {
                val entity = breweryMapper.mapModelToEntity(it)
                breweryRepository.deleteBreweryFromDB(entity)
            }

            withContext(Dispatchers.Main) {
                deleteClicked.call()
            }
        }
        addJob(jobId)
    }
}