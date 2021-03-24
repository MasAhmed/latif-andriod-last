package com.latifapp.latif.ui.base

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.latifapp.latif.R
import com.latifapp.latif.databinding.FragmentBlogsBinding
import com.latifapp.latif.databinding.ToastMsgBinding

import kotlinx.coroutines.flow.collect
import javax.inject.Inject


open abstract class BaseFragment<T : BaseViewModel, B : ViewBinding>() : Fragment(), BaseView<B> {
    @Inject
    lateinit var viewModel: T
    public lateinit var binding: B

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = setBindingView(inflater)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launchWhenStarted {
            viewModel.errorMsg_.collect {
                if (it.isNotEmpty())
                // Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    toastMsg(it, binding.root, requireContext())
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.loader_.collect {
                if (it)
                    showLoader()
                else hideLoader()
            }
        }
    }


}