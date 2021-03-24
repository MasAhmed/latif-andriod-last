package com.latifapp.latif.ui.main.blogs

import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.latifapp.latif.R
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.databinding.FragmentBlogsBinding
import com.latifapp.latif.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BlogsFragment : BaseFragment<BlogsViewModel,FragmentBlogsBinding>() {
    @Inject
    lateinit var adapter_: BlogsAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter_
        }

        lifecycleScope.launchWhenStarted {
            viewModel.getListOfBlogs().collect {
                 adapter_.list= it as MutableList<BlogsModel>
            }
        }



    }

    override fun setBindingView(inflater: LayoutInflater): FragmentBlogsBinding {
        return FragmentBlogsBinding.inflate(inflater)
    }

    override fun showLoader() {
        binding.loader.bar.visibility=View.VISIBLE
    }

    override fun hideLoader() {
        binding.loader.bar.visibility=View.GONE
    }
}