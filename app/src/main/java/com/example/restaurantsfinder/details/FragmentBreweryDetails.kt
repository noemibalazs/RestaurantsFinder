package com.example.restaurantsfinder.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.example.restaurantsfinder.R
import com.example.restaurantsfinder.base.BaseFragment
import com.example.restaurantsfinder.data.Brewery
import com.example.restaurantsfinder.databinding.FragmentBreweryDetailsBinding
import com.example.restaurantsfinder.helper.ACTION_KEY
import com.example.restaurantsfinder.ui.BreweryLandingActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GoogleApiAvailability
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.koin.android.ext.android.inject

class FragmentBreweryDetails : BaseFragment<BreweryDetailsViewModel>(), OnMapReadyCallback {

    private val viewModel: BreweryDetailsViewModel by inject()
    private lateinit var binding: FragmentBreweryDetailsBinding
    private lateinit var mMap: GoogleMap

    override fun initViewModel(): BreweryDetailsViewModel {
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_brewery_details, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initBinding()
        observers()
    }

    private fun initBinding() {
        binding.viewModel = viewModel
        val name = arguments?.getString(ACTION_KEY)
        (activity as BreweryLandingActivity).myToolbar?.title =
            getString(R.string.details_title, name)
        setUpMap()
    }

    private fun observers() {
        viewModel.mutableBrewery.observe(viewLifecycleOwner, Observer {
            populateFields(it)
            showBreweryOnMap(it, mMap)
        })
    }

    private fun populateFields(brewery: Brewery) {
        binding.tvName.text = brewery.name
        binding.tvAddress.text = getString(R.string.street, brewery.street)
        binding.tvCityState.text = getString(R.string.city_state, brewery.city, brewery.state)
        binding.tvWebsite.text = brewery.website_url
        binding.tvPhone.text = brewery.phone
    }

    private fun setUpMap() {

        activity?.let { activity ->

            val isGooglePlayOk =
                GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(activity) == ConnectionResult.SUCCESS
            if (isGooglePlayOk) {
                val mapFragment =
                    childFragmentManager.findFragmentById(R.id.fl_map_container) as SupportMapFragment?
                if (mapFragment == null) {
                    val mapFragmentNew = SupportMapFragment.newInstance()
                    childFragmentManager.beginTransaction()
                        .replace(R.id.fl_map_container, mapFragmentNew).commitAllowingStateLoss()
                    mapFragmentNew.getMapAsync(this)
                } else {
                    mapFragment.getMapAsync(this)
                }
            } else {
                Toast.makeText(activity, getString(R.string.toast_error), Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }

    private fun showBreweryOnMap(brewery: Brewery, google: GoogleMap?) {
        google?.let {
            val markerOptions = MarkerOptions()
            markerOptions.position(
                LatLng(
                    brewery.latitude.toDouble(),
                    brewery.longitude.toDouble()
                )
            )

            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(brewery.latitude.toDouble(), brewery.longitude.toDouble()))
                .zoom(16f).build()

            it.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            it.addMarker(markerOptions)
        }
    }
}