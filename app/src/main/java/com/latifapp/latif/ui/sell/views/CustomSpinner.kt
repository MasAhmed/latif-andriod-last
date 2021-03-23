package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.view.View
import android.widget.*

import android.widget.Spinner.MODE_DIALOG
import android.widget.Spinner.MODE_DROPDOWN
import com.latifapp.latif.R


class CustomSpinner(context_: Context, label: String,val list_: List<String>,action :ViewAction<String>) :
    CustomParentView<String>(context_, label,action), AdapterView.OnItemSelectedListener {



    override fun createView() {

        val spinner= AutoCompleteTextView(context)
         val arrayAdapter = context?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1, list_)
        }

        view=spinner.apply {
            setBackgroundResource(R.drawable.bg_drop_down)
            hint=label
            inputType=0
            setAdapter(arrayAdapter)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 0, 5)
            layoutParams = params
            onItemSelectedListener=this@CustomSpinner
            setTextIsSelectable(true)

            isCursorVisible=false
            isClickable=true
            minHeight=160
            setOnClickListener {
                showDropDown()
            }
            setOnFocusChangeListener{_,_ ->
                showDropDown()
            }
        }
        }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
         action?.getActionId(list_.get(position))
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {

    }


}