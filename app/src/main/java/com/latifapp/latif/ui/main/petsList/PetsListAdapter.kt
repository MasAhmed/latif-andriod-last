package com.latifapp.latif.ui.main.petsList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.PetItemViewBinding


class PetsListAdapter : RecyclerView.Adapter<PetsListAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:PetItemViewBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PetItemViewBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
     }

    override fun getItemCount(): Int =5
}