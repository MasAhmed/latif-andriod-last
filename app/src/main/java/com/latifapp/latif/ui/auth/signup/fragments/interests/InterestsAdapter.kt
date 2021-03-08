package com.latifapp.latif.ui.auth.signup.fragments.interests

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.R
import com.latifapp.latif.databinding.InterestItemBinding

class InterestsAdapter : RecyclerView.Adapter<InterestsAdapter.MyViewHolder>() {
    val list = listOf(
        "Dogs", "Cats", "Birds", "Fishes",
        "Dogs", "Cats", "Birds", "Fishes",
        "Dogs", "Cats", "Birds", "Fishes"
    )

    class MyViewHolder constructor(val binding: InterestItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        var isSelected = false
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): InterestsAdapter.MyViewHolder {
        return MyViewHolder(
            InterestItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: InterestsAdapter.MyViewHolder, position: Int) {
        holder.binding.textItem.setText(list.get(position))
        holder.binding.textItem.setOnClickListener {
            if (holder.isSelected) {
                // make it unselected
                holder.binding.textItem.apply{
                    setBackgroundResource(R.drawable.unselect_bg)
                    setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.sunset_orange))
                }
            } else {
                // make it selected
                holder.binding.textItem.apply{
                    setBackgroundResource(R.drawable.select_bg)
                    setTextColor(ContextCompat.getColor(holder.itemView.context, R.color.white))
                }
            }
            holder.isSelected = !holder.isSelected
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }
}