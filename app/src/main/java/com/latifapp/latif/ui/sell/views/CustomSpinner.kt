package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.latifapp.latif.R

class CustomSpinner(context_: Context, label: String) : CustomParentView(context_, label) {
    override fun createView() {
        val spinner=Spinner(context)
        val list = listOf("reason 1","reason 2","reason 3","reason 4","reason 5")
        val arrayAdapter = context?.let {
            ArrayAdapter<String>(
                it, android.R.layout.simple_list_item_1, list)
        }

        view=spinner.apply {
            background=context.resources.getDrawable(R.drawable.bg_drop_down)
            prompt=label
            adapter=arrayAdapter
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 0, 5)
            layoutParams = params
        }
    }
}