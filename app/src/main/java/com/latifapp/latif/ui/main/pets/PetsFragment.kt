package com.latifapp.latif.ui.main.pets

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityLoginBinding
import com.latifapp.latif.databinding.FragmentPetsBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.pets.bottomDialog.BottomDialogFragment
import com.latifapp.latif.ui.sell.SellActivity
import com.latifapp.latif.utiles.AppConstants
import com.latifapp.latif.utiles.GpsUtils
import com.latifapp.latif.utiles.Permissions
import com.latifapp.latif.utiles.Utiles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

@AndroidEntryPoint
class PetsFragment : BaseFragment<PetsViewModel, FragmentPetsBinding>(),
    PetsAdapter.CategoryActions {

    private var mapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null
    private val petsAdapter = PetsAdapter()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mapFragment == null) {
            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)

            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = petsAdapter
                petsAdapter.action = this@PetsFragment
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

            getCategoriesList()
        }
    }

    private fun getCategoriesList() {
        lifecycleScope.launchWhenStarted {
            viewModel.getCategoriesList(AppConstants.PETS).collect {
                if (!it.isNullOrEmpty()) {
                    petsAdapter.list.addAll(it)
                    petsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun setupmap() {

        val fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(context)
        val task =
            fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener { location: Location ->

            moveToLocation(location)
        }
    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

        // Add a marker in Sydney and move the camera

        if (!Permissions.checkLocationPermissions(requireActivity())) {
            Permissions.showPermissionsDialog(
                activity,
                "Request Location permission Is Needed",
                Permissions.locationManifestPermissionsList,
                0
            )
        } else {
            mMap?.setMyLocationEnabled(true)
            setupmap()
        }
    }

    override fun onResume() {
        super.onResume()
        turnGPSOn()
        mapFragment?.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapFragment?.onStart()
    }

    override fun onPause() {
        super.onPause()
        mapFragment?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapFragment?.onDestroy()
    }

    private fun moveToLocation(location: Location?) {
        if (location != null) {
            Utiles.log_D("ddnndndndndn", "${location}\n $mMap")
            mMap?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location!!.latitude,
                        location!!.longitude
                    ), 15f
                )
            )
        }
    }


    private fun turnGPSOn() {
        GpsUtils(activity).turnGPSOn { isGPSEnable, mlocation -> // turn on GPS
        }
    }

    override fun setBindingView(inflater: LayoutInflater): FragmentPetsBinding {
        return FragmentPetsBinding.inflate(inflater)
    }

    override fun showLoader() {

    }

    override fun hideLoader() {

    }

    override fun selectedCategory(id: Int) {

    }
}
