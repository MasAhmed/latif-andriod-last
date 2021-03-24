package com.latifapp.latif.ui.main.chat.chatHistoryList

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.databinding.FragmentChatHistoryListBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.blogs.BlogsViewModel
import com.latifapp.latif.ui.main.chat.chatPage.ChatPageActivity
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class ChatHistoryListFragment : Fragment(), ChatHistoryListAdapter.Action {

    private lateinit var binding: FragmentChatHistoryListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding=FragmentChatHistoryListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.chatList.apply {
            layoutManager = LinearLayoutManager(context)
            adapter=ChatHistoryListAdapter(this@ChatHistoryListFragment)

        }
    }

    override fun onItemClick() {
        startActivity(Intent(context,ChatPageActivity::class.java))
    }
}