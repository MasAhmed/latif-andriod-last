package com.latifapp.latif.ui.main.pets.bottomDialog

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.latifapp.latif.R
import com.latifapp.latif.databinding.FragmentBottomDialogBinding
import com.latifapp.latif.ui.details.PetImageAdapter
import kotlinx.android.synthetic.main.fragment_bottom_dialog.*



class BottomDialogFragment : BottomSheetDialogFragment() {


    private lateinit var binding: FragmentBottomDialogBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
          binding=FragmentBottomDialogBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onCreate(@Nullable savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.CustomBottomSheetDialogTheme)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager=LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)
            adapter= PetImageAdapter()
        }
     }

}