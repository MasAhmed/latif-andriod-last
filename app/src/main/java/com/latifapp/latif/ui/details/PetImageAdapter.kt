package com.latifapp.latif.ui.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postsapplication.network.NetworkClient
import com.latifapp.latif.R
import com.latifapp.latif.data.models.ImagesModel
import com.latifapp.latif.databinding.PetImageItemBinding

class PetImageAdapter(val images: List<ImagesModel>?) :
    RecyclerView.Adapter<PetImageAdapter.MyViewHolder>() {
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
        val image =images?.get(position)
        if (!image?.image.isNullOrEmpty()) {
            var imagePath=image?.image
            if (!image?.external_link!!)
                imagePath= NetworkClient.BASE_URL +imagePath
            Glide.with(holder.itemView.context).load(imagePath)
                .error(R.drawable.ic_image)
                .placeholder(R.drawable.ic_image).into(holder.binding.image)
        }
    }

    override fun getItemCount(): Int {
        return if (images.isNullOrEmpty()) 0
        else images.size
    }
}