package com.latifapp.latif.ui.main.blogs

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.latifapp.latif.R
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.databinding.BlogItemBinding
import java.io.File
import javax.inject.Inject

class BlogsAdapter @Inject constructor() : RecyclerView.Adapter<BlogsAdapter.MyViewHolder>() {
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
        val img = "${list.get(position).path}${list.get(position).image}"
        if (!img.isNullOrEmpty())
            Glide.with(holder.itemView.context).load(img).error(R.drawable.ic_image)
                .placeholder(R.drawable.ic_image).into(holder.binding.image)
        holder.binding.text.text = list.get(position).title
        holder.binding.authorName.text =
            "${list.get(position).user?.firstName} ${list.get(position).user?.lastName}"
        holder.binding.dateTxt.text = list.get(position).createdDate
    }

    override fun getItemCount(): Int = list.size
}