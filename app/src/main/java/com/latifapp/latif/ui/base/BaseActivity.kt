package com.latifapp.latif.ui.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import javax.inject.Inject

open abstract class BaseActivity<T : BaseViewModel, B : ViewBinding>():AppCompatActivity(),BaseView<B> {
    @Inject
    lateinit var viewModel: T
    public lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=setBindingView(layoutInflater)
        setContentView(binding.root)
    }
}