package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.graphics.Color
import android.graphics.ColorFilter
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.textfield.TextInputLayout
import com.latifapp.latif.R
import com.rengwuxian.materialedittext.MaterialEditText

class CustomEditText(context_: Context, label: String) : CustomParentView(context_, label) {
    override fun createView() {
        val editText = EditText(context)
        view = editText.apply {
            textSize = 15f
            setTextColor(Color.DKGRAY)
            setHintTextColor(Color.GRAY)
            hint = label
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            typeface = ResourcesCompat.getFont(context, R.font.poppins_regular)
            params.setMargins(0, 20, 0, 5)
            layoutParams = params
            DrawableCompat.setTint(
                background,
                ContextCompat.getColor(context, R.color.yellow_orange)
            )
        }

    }
}