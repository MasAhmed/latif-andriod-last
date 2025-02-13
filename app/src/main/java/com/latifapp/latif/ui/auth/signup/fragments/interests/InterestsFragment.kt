package com.latifapp.latif.ui.auth.signup.fragments.interests

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import com.latifapp.latif.databinding.FragmentInterestsBinding
import com.latifapp.latif.ui.main.home.MainActivity
import dagger.hilt.android.AndroidEntryPoint

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [InterestsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@AndroidEntryPoint
class InterestsFragment : Fragment() {
    private val adapter_: InterestsAdapter = InterestsAdapter()
    private lateinit var binding:FragmentInterestsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentInterestsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager=GridLayoutManager(activity,3)
            adapter=adapter_
        }


        binding.doneBtn.setOnClickListener {
            startActivity(Intent(context, MainActivity::class.java))
        }
    }

}