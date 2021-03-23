package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.Switch
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.github.angads25.toggle.interfaces.OnToggledListener
import com.github.angads25.toggle.model.ToggleableView
import com.github.angads25.toggle.widget.LabeledSwitch
import com.latifapp.latif.R
import com.latifapp.latif.databinding.SwitchlayoutBinding
import kotlinx.android.synthetic.main.blog_item.view.*

class CustomSwitch(context_: Context, label: String,action :ViewAction<Boolean>) :
    CustomParentView<Boolean>(context_, label,action), OnToggledListener {
    override fun createView() {
        val switch = SwitchlayoutBinding.inflate(LayoutInflater.from(context))

        switch.apply {
            labelTxt.text=label
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 0, 5)
            root.layoutParams = params
            switchBtn.setOnToggledListener(this@CustomSwitch)
         }


        view=switch.root
    }

    override fun onSwitched(toggleableView: ToggleableView?, isOn: Boolean) {
        action?.getActionId(isOn)
    }
}