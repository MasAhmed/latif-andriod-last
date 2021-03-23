package com.latifapp.latif.ui.sell.views

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.databinding.AddImagesListBinding
import com.latifapp.latif.ui.sell.ImagesAdapter

class CustomImagesList (context_: Context, label: String,val adapter:ImagesAdapter, action: ViewAction<View>) :
    CustomParentView<View>(context_, label,action){


    override fun createView() {
        val binding = AddImagesListBinding.inflate(LayoutInflater.from(context))

        binding.apply {
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.setMargins(0, 20, 0, 5)
            root.layoutParams = params
            addImageBtn.setOnClickListener {
                action?.getActionId(addImageBtn)
                imagesList.layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL
                    ,false)
                imagesList.adapter=adapter
            }
        }


        view=binding.root
    }
}