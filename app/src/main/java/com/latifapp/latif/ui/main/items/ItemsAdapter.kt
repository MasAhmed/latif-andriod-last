package com.latifapp.latif.ui.main.items

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.postsapplication.network.NetworkClient
import com.latifapp.latif.R
import com.latifapp.latif.data.models.AdsModel
import com.latifapp.latif.databinding.ItemViewLayoutBinding
import com.latifapp.latif.databinding.PetItemViewBinding
import com.latifapp.latif.ui.main.petsList.PetsListAdapter

class ItemsAdapter : RecyclerView.Adapter<ItemsAdapter.MyViewHolder>() {
    var list: MutableList<AdsModel> = arrayListOf()
        set(value) {
            field.addAll( value)
            notifyDataSetChanged()
        }
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
        val model = list.get(position)
        holder.binding.petName.text = "${model.name}"
        holder.binding.dateTxt.text = "${model.created_at}"
        holder.binding.priceTxt.text = "${model.price} EGP"
        holder.binding.locTxt.text = "${model.short_description}"

        var image = model.image
        if (!model.external_link)
            image = NetworkClient.BASE_URL + image
        if (!image.isNullOrEmpty())
            Glide.with(holder.itemView.context).load(image)
                .error(R.drawable.ic_image)
                .placeholder(R.drawable.ic_image).into(holder.binding.image)
    }

    override fun getItemCount(): Int =list.size
}