package com.latifapp.latif.utiles

import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.util.Log
import android.view.View
import android.widget.RelativeLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.util.*


object Utiles {

    fun log_D(key: Any, value: Any?) {
        Log.d("$key", "$value")
    }

    fun setMyLocationPositionInBottom(mMapView: View?) {
        val view1 = mMapView?.findViewById("1".toInt()) as View
        val locationButton: View =
            (view1
                .getParent() as View).findViewById("2".toInt())
        val rlp = locationButton.getLayoutParams() as RelativeLayout.LayoutParams
// position on right bottom
// position on right bottom
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, 0)
        rlp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM, RelativeLayout.TRUE)
        rlp.setMargins(0, 0, 20, 20)
    }


}