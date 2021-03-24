package com.latifapp.latif.ui.main.clinic

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
 import com.latifapp.latif.databinding.FragmentClinicBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.blogs.BlogsViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ClinicFragment : Fragment() {


    private lateinit var binding: FragmentClinicBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentClinicBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager= LinearLayoutManager(context)
            adapter= ClinicAdapter()
        }
    }

}