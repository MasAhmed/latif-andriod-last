package com.latifapp.latif.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.PetImageItemBinding

class PetImageAdapter : RecyclerView.Adapter<PetImageAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: PetImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(PetImageItemBinding.inflate(
            LayoutInflater.from(parent.getContext()),
            parent, false
        ))
     }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     }

    override fun getItemCount(): Int =5
}