package com.latifapp.latif.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityZoomingImageBinding

class ZoomingImageActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var binding = ActivityZoomingImageBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }

        val image = intent.extras?.getString("image")
        Glide.with(this).load(image).placeholder(R.drawable.placeholder).into(binding.image)
    }
}