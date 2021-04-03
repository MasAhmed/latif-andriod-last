package com.latifapp.latif.ui.main.pets

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.R
import com.latifapp.latif.data.models.CategoryModel
import com.latifapp.latif.databinding.InterestItemBinding
import com.latifapp.latif.databinding.PetItemLayoutBinding
import com.latifapp.latif.databinding.SelectedPetItemBinding
import com.latifapp.latif.ui.auth.signup.fragments.interests.InterestsAdapter

class PetsAdapter() : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val SELECT_ITEM = 0
    private val UN_SELECT_ITEM = 1
    private var selectedPosition = -1
    var action: CategoryActions? = null
    val list = mutableListOf<CategoryModel>()

    class MyViewHolder constructor(val binding: PetItemLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    class SelectedMyViewHolder constructor(val binding: SelectedPetItemBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == UN_SELECT_ITEM)
            return MyViewHolder(
                PetItemLayoutBinding.inflate(
                    LayoutInflater.from(parent.getContext()),
                    parent, false
                )
            ) else return SelectedMyViewHolder(
            SelectedPetItemBinding.inflate(
                LayoutInflater.from(parent.getContext()),
                parent, false
            )
        )
    }

    override fun getItemViewType(position: Int): Int {
        if (selectedPosition == position)
            return SELECT_ITEM
        else return UN_SELECT_ITEM
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MyViewHolder) {
            holder.itemView.setOnClickListener {
                holder.binding.rootv.apply {
                    selectedPosition = position
                    notifyDataSetChanged()
                    action?.selectedCategory(list.get(position).id!!)
                }

            }
            holder.binding.text.text = list.get(position).name
        } else if (holder is SelectedMyViewHolder) {
            holder.binding.text.text = list.get(position).name
            holder.itemView.setOnClickListener {
                holder.binding.rootv.apply {
                    selectedPosition = -1
                    notifyDataSetChanged()
                    action?.selectedCategory(-1)
                }
            }
        }
    }

    override fun getItemCount(): Int = list.size

    interface CategoryActions {
        fun selectedCategory(id: Int)
    }
}