package com.latifapp.latif.ui.main.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.ItemViewLayoutBinding
import com.latifapp.latif.databinding.PetItemViewBinding
import com.latifapp.latif.ui.main.petsList.PetsListAdapter

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {
    class MyViewHolder(val binding: ItemViewLayoutBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ItemViewLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
    }

    override fun getItemCount(): Int =5
}