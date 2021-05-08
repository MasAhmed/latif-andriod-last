package com.latifapp.latif.ui.subscribe

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.data.models.SubscribeModel
import com.latifapp.latif.databinding.ClinicItemBinding
import com.latifapp.latif.databinding.SubscribItemBinding
import com.latifapp.latif.ui.main.clinic.ClinicAdapter


class SubscribeAdapter : RecyclerView.Adapter<SubscribeAdapter.MyViewHolder>() {
     var list = mutableListOf<SubscribeModel>()
    set(value) {
        field.addAll(value)
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: SubscribItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            SubscribItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.text.text = list.get(position).name
    }

    override fun getItemCount(): Int = list.size
}