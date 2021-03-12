package com.latifapp.latif.ui.main.blogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.BlogItemBinding

class BlogsAdapter : RecyclerView.Adapter<BlogsAdapter.MyViewHolder>() {
    private val list = listOf("Topic #1", "Topic #2", "Topic #3", "Topic #4", "Topic #5")

    class MyViewHolder(val binding: BlogItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            BlogItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.text.text = list.get(position)
    }

    override fun getItemCount(): Int = list.size
}