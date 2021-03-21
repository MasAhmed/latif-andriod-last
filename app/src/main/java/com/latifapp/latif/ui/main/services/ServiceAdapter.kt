package com.latifapp.latif.ui.main.services

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ClinicItemBinding
import com.latifapp.latif.databinding.ServicesItemBinding
import com.latifapp.latif.ui.main.clinic.ClinicAdapter

class ServiceAdapter : RecyclerView.Adapter<ServiceAdapter.MyViewHolder>() {
    val list = listOf<ClinicAdapter.Model>(
        ClinicAdapter.Model(R.string.housing, R.drawable.housing),
        ClinicAdapter.Model(R.string.hostelry, R.drawable.hostel),
        ClinicAdapter.Model(R.string.delivery, R.drawable.delivery_man),
        ClinicAdapter.Model(R.string.shower, R.drawable.shower)
    )

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
        holder.binding.image.setImageResource(list.get(position).image)
        holder.binding.title.setText(list.get(position).title)
    }

    override fun getItemCount(): Int = list.size
}