package com.latifapp.latif.ui.main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.R
import com.latifapp.latif.databinding.MenuItemBinding

class MenuAdapter : RecyclerView.Adapter<MenuAdapter.MyViewHolder>() {
    val list= listOf(R.string.pets,R.string.items,
        R.string.service,R.string.blogs,
        R.string.profile)
    class MyViewHolder(val binding:MenuItemBinding) :RecyclerView.ViewHolder(binding.root){

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(MenuItemBinding.inflate(
           LayoutInflater.from(parent.getContext()),
           parent, false
       ))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.text.setText(list.get(position))
    }

    override fun getItemCount(): Int = list.size
}