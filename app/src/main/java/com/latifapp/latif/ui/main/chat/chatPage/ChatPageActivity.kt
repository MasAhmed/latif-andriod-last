package com.latifapp.latif.ui.main.chat.chatPage

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.databinding.ActivityChatPageBinding

class ChatPageActivity : AppCompatActivity() {
    private lateinit var binding: ActivityChatPageBinding
    private val adapter_ = ChatPageAdapter()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChatPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener {
            onBackPressed()
        }
        setList()
        binding.sendBtn.setOnClickListener {
            if (!binding.typeMsg.text.toString().isEmpty()) {
                adapter_.apply {
                    addComment(binding.typeMsg.text.toString())
                    binding.list.scrollToPosition(itemCount - 1)
                }
                binding.typeMsg.text = null
            }
        }
    }

    private fun setList() {
        binding.list.apply {
            layoutManager = LinearLayoutManager(this@ChatPageActivity)
            adapter = adapter_
        }
    }
}