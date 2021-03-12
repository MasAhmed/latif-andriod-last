package com.latifapp.latif.ui.subscribe

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.R
import com.latifapp.latif.databinding.ActivitySubscribBinding

class SubscribeActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySubscribBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySubscribBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title.text = getString(R.string.subscribe)
        binding.toolbar.backBtn.setOnClickListener {
            onBackPressed()
        }
        setlist()
    }

    private fun setlist() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@SubscribeActivity)
            adapter = SubscribeAdapter()
        }
    }
}