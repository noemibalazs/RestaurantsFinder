package com.example.restaurantsfinder.city

import androidx.databinding.Observable
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.restaurantsfinder.base.BaseViewModel
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.datasource.BreweryRepository
import kotlinx.coroutines.launch


class BreweriesByCityViewModel(
    val breweryRepository: BreweryRepository
) : BaseViewModel() {

    val mutableCityName = ObservableField<String>()
    private var _mutableBreweryCityList = MutableLiveData<Brewery>()
    val mutableBreweryCityList: LiveData<Brewery>
        get() = _mutableBreweryCityList

    val mutablePropertyChangeListener = object : Observable.OnPropertyChangedCallback() {
        override fun onPropertyChanged(sender: Observable?, propertyId: Int) {

        }
    }

    init {

    }

    private fun loadCity(city: String){
        val joId = launch {
            val result = breweryRepository.breweryRemoteDataSource.getBreweriesByCity(city)
        }
    }

    fun addBreweryToDB(brewery: Brewery){}
}