package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.latifapp.latif.R
import com.latifapp.latif.utiles.NothingSelectedSpinnerAdapter
import com.skydoves.powerspinner.PowerSpinnerView
import com.skydoves.powerspinner.SpinnerAnimation
import com.skydoves.powerspinner.createPowerSpinnerView

class CustomSpinner(context_: Context, label: String,val list_: List<String>,action :ViewAction<String>) :
    CustomParentView<String>(context_, label,action) {



    override fun createView() {
        val spinner= PowerSpinnerView(context)
         val arrayAdapter = context?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1, list_)
        }

        view=spinner.apply {
            hint=label
            setItems(list_)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            spinnerPopupAnimation= SpinnerAnimation.BOUNCE
            params.setMargins(0, 40, 0, 5)
            spinnerPopupBackgroundColor=Color.WHITE
            spinnerPopupElevation=14
            minHeight=170
            dividerSize=3
            showDivider=true
            dividerColor=Color.GRAY
            spinnerPopupHeight=1000
            dismissWhenNotifiedItemSelected=true

            gravity=Gravity.CENTER_VERTICAL
            layoutParams = params
            setBackgroundResource(R.drawable.bg_drop_down)
            setOnSpinnerItemSelectedListener<String> { oldIndex, oldItem, newIndex, newText ->
                action?.getActionId(list_.get(newIndex))

            }
        }



    }

}