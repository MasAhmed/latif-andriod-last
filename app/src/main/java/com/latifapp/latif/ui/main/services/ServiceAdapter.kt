package com.latifapp.latif.ui.main.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.ClinicItemBinding
import com.latifapp.latif.databinding.ServicesItemBinding

class ServiceAdapter  : RecyclerView.Adapter<ServiceAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ServicesItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ServicesItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int = 5
}