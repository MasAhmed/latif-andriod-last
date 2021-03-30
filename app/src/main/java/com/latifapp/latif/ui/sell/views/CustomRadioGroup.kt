package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import com.latifapp.latif.data.models.OptionsModel
import com.latifapp.latif.databinding.CheckboxsGroupLayoutBinding
import com.latifapp.latif.ui.sell.adapters.CheckBoxsListAdapter
import com.latifapp.latif.ui.sell.adapters.RadioButtonAdapter

class CustomRadioGroup(
    context_: Context,
    label: String,
    val list_: List<OptionsModel>,
    action: ViewAction<String>
) :
    CustomParentView<String>(context_, label, action), CheckBoxsListAdapter.CheckBoxAction {
    override fun createView() {
        val binding= CheckboxsGroupLayoutBinding.inflate(LayoutInflater.from(context))
        binding.checkBoxsGroup.apply {
            layoutManager= GridLayoutManager(context,3)
            adapter= RadioButtonAdapter(list_,this@CustomRadioGroup)
        }
        binding.label.text=label
        view=binding.root
    }

    override fun getChecked(text: String) {
        action?.getActionId(text)
    }
}