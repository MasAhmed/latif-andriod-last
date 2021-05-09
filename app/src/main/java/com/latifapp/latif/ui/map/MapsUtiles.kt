package com.latifapp.latif.ui.map

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*

object MapsUtiles {
    fun getMyLocationName(context: Context, latlng: LatLng) : MutableLiveData<String> {
        val liveData= MutableLiveData<String>("")
        CoroutineScope(Dispatchers.IO).launch {
            val geocoder: Geocoder = Geocoder(context, Locale("ar"));

            try {
                val addresses: List<Address> =
                    geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1)
                val obj: Address? = addresses?.get(0)
               // val add = obj?.getSubThoroughfare()+","+obj?.

                var add = obj?.getAddressLine(0)


                Log.e("IGA", "Address" + add)
                liveData.postValue(add)
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return liveData
    }

    fun getCityName(context: Context, latlng: LatLng,lang:String) : MutableLiveData<String> {
        val liveData= MutableLiveData<String>("")
        CoroutineScope(Dispatchers.IO).launch {
            val geocoder: Geocoder = Geocoder(context, Locale(lang));

            try {
                val addresses: List<Address> =
                    geocoder.getFromLocation(latlng.latitude, latlng.longitude, 1)
                if (!addresses.isNullOrEmpty()) {
                    val obj: Address? = addresses?.get(0)
                    // val add = obj?.getSubThoroughfare()+","+obj?.

                    var add = obj?.adminArea


                    Log.e("IGA", "Address" + add)
                    liveData.postValue(add)
                }
            } catch (e: IOException) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                //Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
        return liveData
    }
}