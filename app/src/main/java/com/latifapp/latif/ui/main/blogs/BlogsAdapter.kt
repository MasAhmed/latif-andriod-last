package com.latifapp.latif.ui.main.blogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.databinding.BlogItemBinding
import javax.inject.Inject

class BlogsAdapter @Inject constructor(): RecyclerView.Adapter<BlogsAdapter.MyViewHolder>() {
    var list = mutableListOf<BlogsModel>()
    set(value) {
        field.addAll(value)
        notifyDataSetChanged()

    }

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
        holder.binding.text.text = list.get(position).title
    }

    override fun getItemCount(): Int = list.size
}