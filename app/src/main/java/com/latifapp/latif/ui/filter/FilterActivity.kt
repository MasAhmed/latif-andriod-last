package com.latifapp.latif.ui.filter

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivityFilterBinding

class FilterActivity : AppCompatActivity() {
    private lateinit var binding:ActivityFilterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityFilterBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.applyFilterBtn.setOnClickListener {
            onBackPressed()
        }
        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
    }
}