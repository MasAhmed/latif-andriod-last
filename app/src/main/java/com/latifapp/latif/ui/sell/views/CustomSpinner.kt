package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.text.InputType
import android.view.LayoutInflater
import android.view.View
import android.widget.*

import android.widget.Spinner.MODE_DIALOG
import android.widget.Spinner.MODE_DROPDOWN
import com.latifapp.latif.R
import com.latifapp.latif.data.models.OptionsModel
import com.latifapp.latif.databinding.CustomSpinnerLayoutBinding


class CustomSpinner(context_: Context, label: String,val list_: List<OptionsModel>,action :ViewAction<String>) :
    CustomParentView<String>(context_, label,action),
      AdapterView.OnItemSelectedListener {



    override fun createView() {
        val list:List<String> =list_.map { "${it.label}" }

        val spinner= CustomSpinnerLayoutBinding.inflate(LayoutInflater.from(context))
         val arrayAdapter = context?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1, list)
        }

        view=spinner.apply {

            label.text=this@CustomSpinner.label
            adsTypeSpinner.setAdapter(arrayAdapter)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 40, 0, 5)
            root.layoutParams = params
            adsTypeSpinner.onItemSelectedListener=this@CustomSpinner

        }.root
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        action?.getActionId(list_.get(position).code!!)
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
     }


}