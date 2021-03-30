package com.latifapp.latif.ui.sell.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.data.models.OptionsModel
import com.latifapp.latif.databinding.CheckboxLayoutBinding
import com.latifapp.latif.databinding.PetImageItemBinding

class CheckBoxsListAdapter(val list:List<OptionsModel>,val action:CheckBoxAction) : RecyclerView.Adapter<CheckBoxsListAdapter.MyViewHolder>() {
    private val selectedTexts:MutableSet<String> = mutableSetOf()
    class MyViewHolder (val binding: CheckboxLayoutBinding) : RecyclerView.ViewHolder(binding.root){
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CheckBoxsListAdapter.MyViewHolder {
        return MyViewHolder(
            CheckboxLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            ))
    }

    override fun onBindViewHolder(holder: CheckBoxsListAdapter.MyViewHolder, position: Int) {
       holder.binding.checkbox.text=list.get(position).label
        holder.binding.checkbox.setOnCheckedChangeListener{ compoundButton: CompoundButton, b: Boolean ->
            if (b)
                selectedTexts.add(list.get(position).code!!)
            else selectedTexts.remove(list.get(position).code)
            var txt=""
            for (s in selectedTexts){
                txt+="$s,"
            }
            txt = txt.substring(0, txt.length - 1)
            action.getChecked(txt)
        }
    }

    override fun getItemCount(): Int=list.size
    interface CheckBoxAction{
        fun getChecked(text:String)
    }
}