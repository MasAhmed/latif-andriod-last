package com.latifapp.latif.ui.map

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.latifapp.latif.R
import com.latifapp.latif.utiles.AppConstants
import com.latifapp.latif.utiles.GpsUtils
import com.latifapp.latif.utiles.Permissions
import com.latifapp.latif.utiles.Utiles
import java.lang.Exception


class MapsActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        turnGPSOn()
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera

        if (!Permissions.checkLocationPermissions(this)) {
            Permissions.showPermissionsDialog(
                this,
                "Request Location permission Is Needed",
                Permissions.locationManifestPermissionsList,
                0
            )
        } else {
            mMap.setMyLocationEnabled(true)
             buildGoogleApiClient()
        }


    }

    private fun buildGoogleApiClient() {


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == AppConstants.GPS_REQUEST) {
            // flag maintain before get location
        }

    }

    @SuppressLint("MissingPermission")
    private fun turnGPSOn() {
        GpsUtils(this).turnGPSOn { isGPSEnable, mlocation -> // turn on GPS

             try {
                val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
                locationManager.requestLocationUpdates(
                    LocationManager.NETWORK_PROVIDER,
                    0L,
                    10f,
                    this
                ) //You can also use LocationManager.GPS_PROVIDER and LocationManager.PASSIVE_PROVIDER


            } catch (e: Exception) {

            }

        }
    }

    override fun onLocationChanged(location: Location) {
        Utiles.log_D("locationlocation", location)
        mMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(
                    location!!.latitude,
                    location!!.longitude
                ), 15f
            )
        )
        mMap?.animateCamera(CameraUpdateFactory.zoomTo(14f), 2000, null)
    }
}