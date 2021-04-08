package com.latifapp.latif.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.Dispatchers

import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject


open abstract class BaseFragment<viewmodel : BaseViewModel, viewbinding : ViewBinding>() :
    Fragment(), BaseView<viewbinding> {
    @Inject
    lateinit var viewModel: viewmodel
    public lateinit var binding: viewbinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        if (!::binding.isInitialized)
            binding = setBindingView(inflater)
        return binding.getRoot()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.errorMsg_.observe(viewLifecycleOwner, Observer {
            if (it.isNotEmpty())
            // Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                toastMsg_Warning(it, binding.root, requireContext())
        })

        lifecycleScope.launchWhenStarted {
            withContext(Dispatchers.Main) {
                viewModel.loader_.collect {
                    if (it)
                        showLoader()
                    else hideLoader()
                }

            }
        }
    }


}