package com.latifapp.latif.ui.main.blogs

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.R
import com.latifapp.latif.databinding.FragmentBlogsBinding


class BlogsFragment : Fragment() {


    private lateinit var binding: FragmentBlogsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         binding=FragmentBlogsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter=BlogsAdapter()
        }

    }
}