package com.latifapp.latif.ui.main.pets

import android.annotation.SuppressLint
import android.app.ProgressDialog.show
import android.content.Intent
import android.graphics.drawable.GradientDrawable
import android.location.Location
import androidx.fragment.app.Fragment

import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.latifapp.latif.R
import com.latifapp.latif.databinding.FragmentPetsBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.blogs.BlogsViewModel
import com.latifapp.latif.ui.main.pets.bottomDialog.BottomDialogFragment
import com.latifapp.latif.ui.sell.SellActivity
import com.latifapp.latif.utiles.AppConstants
import com.latifapp.latif.utiles.GpsUtils
import com.latifapp.latif.utiles.Permissions
import com.latifapp.latif.utiles.Utiles
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsFragment : Fragment() {

    private var mMap: GoogleMap? = null
    private lateinit var binding: FragmentPetsBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPetsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = PetsAdapter()
        }

        binding.pin.setOnClickListener {
            Utiles.log_D("dndndndndndn", "HERE")

            childFragmentManager.let {
                BottomDialogFragment().apply {
                    show(it, tag)
                }
            }
        }

        binding.sellBtn.setOnClickListener {
            startActivity(Intent(activity, SellActivity::class.java))
        }
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

        // Add a marker in Sydney and move the camera

        if (!Permissions.checkLocationPermissions(requireContext())) {
            Permissions.showPermissionsDialog(
                activity,
                "Request Location permission Is Needed",
                Permissions.locationManifestPermissionsList,
                0
            )
        } else {
            mMap?.setMyLocationEnabled(true)
//            fusedLocationProviderClient.requestLocationUpdates(
//                locationRequest,
//                locationCallback,
//                Looper.getMainLooper()
//            )
        }
    }

    override fun onResume() {
        super.onResume()
        turnGPSOn()
    }

    val fusedLocationProviderClient by lazy {
        LocationServices.getFusedLocationProviderClient(requireContext())
    }

    val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            locationResult ?: return
            for (location in locationResult.locations){
                moveToLocation(location)
            }
        }
    }

    private fun moveToLocation(location: Location?) {
        Utiles.log_D("ddnndndndndn","${location}")
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

    val locationRequest = LocationRequest.create().apply {
        interval = 10_000
        fastestInterval = 5_000
        priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }
    private fun turnGPSOn() {
        GpsUtils(activity).turnGPSOn { isGPSEnable, mlocation -> // turn on GPS

        }
    }
}
