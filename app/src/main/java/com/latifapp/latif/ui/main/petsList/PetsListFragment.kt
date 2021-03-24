package com.latifapp.latif.ui.main.petsList

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.R
import com.latifapp.latif.databinding.FragmentPetsListBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.blogs.BlogsViewModel
import com.latifapp.latif.ui.main.pets.PetsAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class PetsListFragment : Fragment() {
    private lateinit var binding:FragmentPetsListBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentPetsListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setList();
    }

    private fun setList() {
        binding.petsListRecyclerView.apply {
            layoutManager=LinearLayoutManager(context)
            adapter= PetsListAdapter()
        }

        binding.petsCatgRecyclerView.apply {
            layoutManager=LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
            adapter= PetsAdapter()
        }
    }
}