package com.latifapp.latif.ui.main.clinic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.ClinicItemBinding


class ClinicAdapter : RecyclerView.Adapter<ClinicAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ClinicItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ClinicItemBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent, false
        ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int =5
}