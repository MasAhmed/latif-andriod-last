package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.latifapp.latif.R


class CustomEditText(
    context_: Context,
    label: String,
    val isText: Boolean,
    action: ViewAction<String>
) : CustomParentView<String>(
    context_,
    label,
    action
),
    TextWatcher {
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
            addTextChangedListener(this@CustomEditText)
            inputType = if (!isText) InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT
        }

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        action?.getActionId(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }
}