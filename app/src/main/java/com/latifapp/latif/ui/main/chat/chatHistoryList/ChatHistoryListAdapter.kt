package com.latifapp.latif.ui.main.chat.chatHistoryList

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.ChatHistoryItemLayoutBinding
import com.latifapp.latif.databinding.PetImageItemBinding
import com.latifapp.latif.ui.auth.signup.fragments.interests.InterestsAdapter
import com.latifapp.latif.ui.details.PetImageAdapter

class ChatHistoryListAdapter(val action:Action) : RecyclerView.Adapter<ChatHistoryListAdapter.MyViewHolder>() {
    class MyViewHolder(val binding:ChatHistoryItemLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(
            ChatHistoryItemLayoutBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        holder.itemView.setOnClickListener {
            action.onItemClick()
        }
    }

    override fun getItemCount(): Int =7

    interface Action{
        fun onItemClick()
    }
}