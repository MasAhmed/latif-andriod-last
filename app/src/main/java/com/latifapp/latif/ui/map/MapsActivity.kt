package com.latifapp.latif.ui.map

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.latifapp.latif.R
import com.latifapp.latif.utiles.GpsUtils
import com.latifapp.latif.utiles.Permissions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

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
        }


    }

    private fun turnGPSOn() {
        GpsUtils(this).turnGPSOn { isGPSEnable -> // turn on GPS
            Log.d("dndnndndndnnd","$isGPSEnable")
        }
    }
}