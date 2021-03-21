package com.latifapp.latif.ui.main.clinic

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ClinicItemBinding


class ClinicAdapter : RecyclerView.Adapter<ClinicAdapter.MyViewHolder>() {

    val list= listOf<Model>(Model(R.string.veterinary, R.drawable.veterinary),
        Model(R.string.pharmacy, R.drawable.pharmacy))
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
        holder.binding.image.setImageResource(list.get(position).image)
        holder.binding.title.setText(list.get(position).title)
    }

    override fun getItemCount(): Int =list.size

    data class Model(val title:Int,val image:Int)
}