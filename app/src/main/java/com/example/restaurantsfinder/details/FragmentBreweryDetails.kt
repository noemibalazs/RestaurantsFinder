package com.example.restaurantsfinder.details

import android.os.Bundle
import android.os.Handler
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
import com.example.restaurantsfinder.helper.BreweryGPS
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
    private var handler = Handler()
    private var runnable: Runnable? = null

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
    }

    private fun observers() {
        viewModel.mutableBrewery.observe(viewLifecycleOwner, Observer {
            populateFields(it)
        })

        viewModel.mutableBreweryGPSPosition.observe(viewLifecycleOwner, Observer {
            onGPSPositionChanged(it)
        })
    }

    private fun populateFields(brewery: Brewery) {
        binding.tvName.text = brewery.name
        binding.tvAddress.text = getString(R.string.street, brewery.street)
        binding.tvCityState.text = getString(R.string.city_state, brewery.city, brewery.state)
        binding.tvWebsite.text = brewery.website_url
        binding.tvPhone.text = brewery.phone
    }

    private fun onGPSPositionChanged(breweryGPS: BreweryGPS) {
        runnable = Runnable {
            setUpGpsPosition()
        }
        handler.postDelayed(runnable, 350L)
    }

    private fun setUpGpsPosition() {

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
                Toast.makeText(activity, "Hey, try it again!", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        viewModel.mutableBreweryGPSPosition.observe(viewLifecycleOwner, Observer { breweryGps ->
            val markerOptions = MarkerOptions()
            markerOptions.position(LatLng(breweryGps.lat, breweryGps.lng))

            val cameraPosition = CameraPosition.Builder()
                .target(LatLng(breweryGps.lat, breweryGps.lng))
                .zoom(16f).build()

            googleMap.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
            googleMap.addMarker(markerOptions)

            googleMap.uiSettings.isZoomControlsEnabled = false
            googleMap.uiSettings.isZoomGesturesEnabled = false
            googleMap.uiSettings.setAllGesturesEnabled(false)
            googleMap.uiSettings.isMapToolbarEnabled = false

            // Disable marker click
            googleMap.setOnMarkerClickListener { true }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        runnable = null
    }

}