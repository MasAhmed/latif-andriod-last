package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.graphics.Color
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.EditText
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.latifapp.latif.R
import com.latifapp.latif.databinding.CustomEdittextLayoutBinding


class CustomEditText(
    context_: Context,
    label: String="",
    val isText: Boolean,
    action: ViewAction<String>
) : CustomParentView<String>(
    context_,
    label,
    action
),
    TextWatcher {
    override fun createView() {
        val editText = CustomEdittextLayoutBinding.inflate(LayoutInflater.from(context))
        view = editText.apply {
             labeltxt.text="$label "
            editext.hint = label
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
             params.setMargins(0, 40, 0, 5)
            root.layoutParams = params

            editext.addTextChangedListener(this@CustomEditText)
            editext.inputType = if (!isText) InputType.TYPE_CLASS_NUMBER else InputType.TYPE_CLASS_TEXT
        }.root

    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
        action?.getActionId(s.toString())
    }

    override fun afterTextChanged(s: Editable?) {
    }
}