package com.latifapp.latif.ui.sell

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latifapp.latif.databinding.PetImageItemBinding
import com.latifapp.latif.ui.details.PetImageAdapter
import java.io.File

class ImagesAdapter : RecyclerView.Adapter<ImagesAdapter.MyViewHolder>() {
    val list = mutableListOf<String>()

    class MyViewHolder(val binding: PetImageItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            PetImageItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(holder.itemView.context).load(File(list.get(position))).into(holder.binding.image)

    }

    override fun getItemCount(): Int = list.size
}