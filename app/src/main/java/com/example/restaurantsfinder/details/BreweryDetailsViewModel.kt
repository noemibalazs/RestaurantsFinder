package com.example.restaurantsfinder.details

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.base.SingleLiveData
import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import com.example.restaurantsfinder.helper.BreweryGPS
import com.example.restaurantsfinder.helper.OnClickEvent
import com.example.restaurantsfinder.helper.SharedPrefHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BreweryDetailsViewModel(
    private val breweryRepository: BreweryRepository,
    private val sharedPrefHelper: SharedPrefHelper
) : BaseViewModel() {

    private val TAG = BreweryDetailsViewModel::class.java.name

    private var _mutableBrewery: MutableLiveData<Brewery> = MutableLiveData()
    val mutableBrewery: LiveData<Brewery>
        get() = _mutableBrewery

    val mutableBreweryGPSPosition = SingleLiveData<BreweryGPS>()

    init {
        loadBreweryById()
    }

    private fun loadBreweryById() {
        val jobId = launch {
            val result =
                breweryRepository.breweryRemoteDataSource.getBreweryById(sharedPrefHelper.getBreweryId())
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
        Log.d(TAG, "onBreweryResponse throw: $failure brewery: ${brewery?.name}")

        failure?.let {
            Log.d(TAG, "onBreweryResponse throw failure, see message: ${it.message}")
        }

        brewery?.let {
            _mutableBrewery.value = it
            setGpsPosition(it)
        }
    }

    private fun setGpsPosition(brewery: Brewery) {
        val latitude = brewery.latitude.toDouble()
        val longitude = brewery.longitude.toDouble()
        mutableBreweryGPSPosition.value == BreweryGPS(
            lat = latitude,
            lng = longitude
        )
    }
}