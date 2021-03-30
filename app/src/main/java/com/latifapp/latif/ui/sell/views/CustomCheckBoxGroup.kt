package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.view.LayoutInflater
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.latifapp.latif.data.models.OptionsModel
import com.latifapp.latif.databinding.CheckboxsGroupLayoutBinding
import com.latifapp.latif.ui.sell.adapters.CheckBoxsListAdapter
import java.util.*


class CustomCheckBoxGroup(
    context_: Context,
    label: String,
    val list_: List<OptionsModel>,
    action: ViewAction<String>
) :
    CustomParentView<String>(context_, label, action), CheckBoxsListAdapter.CheckBoxAction {
    override fun createView() {
        val binding=CheckboxsGroupLayoutBinding.inflate(LayoutInflater.from(context))
        binding.checkBoxsGroup.apply {
            layoutManager=GridLayoutManager(context,3)
            adapter=CheckBoxsListAdapter(list_,this@CustomCheckBoxGroup)
        }
        binding.label.text=label
        view=binding.root
    }

    override fun getChecked(text: String) {
        action?.getActionId(text)
    }
}