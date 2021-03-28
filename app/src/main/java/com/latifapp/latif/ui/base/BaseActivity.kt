package com.latifapp.latif.ui.base

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.viewbinding.ViewBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.withContext
import javax.inject.Inject

open abstract class BaseActivity<viewmodel : BaseViewModel, viewbind : ViewBinding>():AppCompatActivity(),BaseView<viewbind> {
    @Inject
    lateinit var viewModel: viewmodel
    public lateinit var binding: viewbind

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = setBindingView(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launchWhenStarted {
            viewModel.errorMsg_.collect {
                if (it.isNotEmpty())
                // Toast.makeText(context, it, Toast.LENGTH_SHORT).show()
                    toastMsg(it, binding.root, this@BaseActivity)
            }
        }

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