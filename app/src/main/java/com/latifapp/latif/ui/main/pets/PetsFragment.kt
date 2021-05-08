package com.latifapp.latif.ui.main.pets

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.ui.IconGenerator
import com.latifapp.latif.R
import com.latifapp.latif.data.models.AdsModel
import com.latifapp.latif.databinding.CustomMarkserBinding
import com.latifapp.latif.databinding.FragmentPetsBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.home.MainActivity
import com.latifapp.latif.ui.main.pets.bottomDialog.BottomDialogFragment
import com.latifapp.latif.ui.map.MapsUtiles
import com.latifapp.latif.ui.sell.SellActivity
import com.latifapp.latif.utiles.AppConstants
import com.latifapp.latif.utiles.GpsUtils
import com.latifapp.latif.utiles.Permissions
import com.latifapp.latif.utiles.Utiles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.coroutines.flow.collect


@AndroidEntryPoint
class PetsFragment : BaseFragment<PetsViewModel, FragmentPetsBinding>(),
    PetsAdapter.CategoryActions  {

    private val mapSets= mutableSetOf<AdsModel>()
    private var category: Int?=null
    private var mapFragment: SupportMapFragment? = null
    private var mMap: GoogleMap? = null
    private val petsAdapter = PetsAdapter()
    companion object{
        var Latitude_=0.0
        var Longitude_=0.0
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (mapFragment == null) {
            mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
            Utiles.setMyLocationPositionInBottom(mapFragment?.view)
            binding.recyclerView.apply {
                layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                adapter = petsAdapter
                petsAdapter.action = this@PetsFragment
            }

//            binding.pin.setOnClickListener {
//                Utiles.log_D("dndndndndndn", "HERE")
//
//                childFragmentManager.let {
//                    BottomDialogFragment().apply {
//                        show(it, tag)
//                    }
//                }
//            }

            binding.sellBtn.setOnClickListener {
                startActivity(Intent(activity, SellActivity::class.java))
            }

            getCategoriesList()

        }
    }

    private fun getPetsList() {

        lifecycleScope.launchWhenStarted {
            viewModel.getItems(AppConstants.PETS_STR, category).collect {
                viewModel.page=0
                if (it!=null) {
                    mapSets.addAll(it)
                    Utiles.log_D("ncncncnncncncn", "${mapSets.size}")
                    setLPetsLocations()
                }
            }
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

        task.addOnCompleteListener {
            val location = task.result
            if (location != null)
                moveToLocation(location)
        }


    }

    @SuppressLint("MissingPermission")
    private val callback = OnMapReadyCallback { googleMap ->

        mMap = googleMap

        if (!Permissions.checkLocationPermissions(requireActivity())) {
            Permissions.showPermissionsDialog(
                activity,
                "Request Location permission Is Needed",
                Permissions.locationManifestPermissionsList,
                0
            )
        } else {
            mMap?.setMyLocationEnabled(true)
            //setupmap()
        }

        mMap?.setOnCameraIdleListener {
            var latLng = mMap?.cameraPosition?.target
            if (latLng != null && latLng.latitude != 0.0) {
                Latitude_=latLng.latitude
                Longitude_=latLng.longitude
                getPetsList()
                getCityName_(latLng)
            }
        }
    }

    private fun getCityName_(latLng: LatLng) {

        MapsUtiles.getCityName(requireContext(), latLng, "en").observe(
            viewLifecycleOwner,
            Observer {
                if (!it.isNullOrEmpty()) {
                    val city = it.replace("Governorate", "")
                    (activity as MainActivity).toolBarTitle.text = city
                }
            })
    }

    fun setLPetsLocations() {
        if (mMap != null) {
           mMap?.clear()
            mapSets.forEach { adsModel->
                val pet = LatLng(adsModel.latitude, adsModel.longitude)
                val iconGenerator = IconGenerator(context)
                 val inflatedViewBinding = CustomMarkserBinding.inflate(layoutInflater)
//                val imageView=inflatedViewBinding.image
//                if (!adsModel.image.isNullOrEmpty()) {
//                    Glide.with(requireActivity()).load(adsModel.image).into(imageView)
//                    imageView.visibility = View.VISIBLE
//                }
                val TRANSPARENT_DRAWABLE: Drawable = ColorDrawable(Color.TRANSPARENT)
                iconGenerator.setBackground(TRANSPARENT_DRAWABLE)
                iconGenerator.setContentView(inflatedViewBinding.root)
                var marker = MarkerOptions().position(pet)
                    .title(adsModel.name) // below line is use to add custom marker on our map.
                    .icon(BitmapDescriptorFactory.fromBitmap(iconGenerator.makeIcon()))

                mMap?.addMarker(
                    marker
                )?.tag =(adsModel)

                mMap?.setOnMarkerClickListener { marker->
                    val model=marker.tag as AdsModel
                    if (model!=null) {
                        val bundle = Bundle()
                        bundle.putParcelable("model", model)
                        childFragmentManager.let {
                            BottomDialogFragment().apply {
                                arguments = bundle
                                show(it, tag)
                            }
                        }
                    }
                    true
                }
            }
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
        if (!Permissions.checkLocationPermissions(requireContext())) {
            Permissions.showPermissionsDialog(
                requireContext(),
                "Request Location permission Is Needed",
                Permissions.locationManifestPermissionsList,
                0
            )
        } else
            GpsUtils(activity).turnGPSOn { isGPSEnable, mlocation -> // turn on GPS
                Utiles.log_D("fnnfnfnfnnf", isGPSEnable)
                if (isGPSEnable)
                    setupmap()


            }
    }

    override fun setBindingView(inflater: LayoutInflater): FragmentPetsBinding {
        return FragmentPetsBinding.inflate(inflater)
    }

    override fun showLoader() {

    }

    override fun hideLoader() {

    }

    override fun selectedCategory(id: Int?) {
        Utiles.log_D("ndndndndnnd", "${id}")
        category=id
        mMap?.clear()
        mapSets.clear()
        getPetsList()
    }
}
