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
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.R
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.databinding.FragmentBlogsBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.home.MainActivity
import com.latifapp.latif.ui.main.pets.PetsAdapter
import com.latifapp.latif.utiles.Utiles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.toolbar.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BlogsFragment : BaseFragment<BlogsViewModel, FragmentBlogsBinding>(),
    PetsAdapter.CategoryActions {
    private var isLoadingData: Boolean = true

    @Inject
    lateinit var adapter_: BlogsAdapter
    private val petsAdapter = PetsAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).searchBtn.setOnClickListener {
            Utiles.log_D("snnsnsnnsns","ssnsnsn")
        }

        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapter_
            addOnScrollListener(scrollListener)


        }
        binding.categoryRecyclerView.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = petsAdapter
            petsAdapter.action = this@BlogsFragment
        }



        getBlogs()
        getBlogsCategoryList()

    }

    override fun selectedCategory(id: Int) {
        if (id == -1) {
            //get all blogs
        } else {

        }
    }

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val totalItemCount = layoutManager?.itemCount
            if (!isLoadingData && totalItemCount <= layoutManager.findLastVisibleItemPosition() + 2) {
                isLoadingData = true
                getBlogs()
            }
        }
    }

    private fun getBlogsCategoryList() {
        lifecycleScope.launchWhenStarted {
            viewModel.getBlogsCategoryList().collect {

                if (!it.isNullOrEmpty()) {
                    petsAdapter.list.addAll(it)
                    petsAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun getBlogs() {
        lifecycleScope.launchWhenStarted {
            viewModel.getListOfBlogs().collect {
                Utiles.log_D("dndnnnndndn", it)
                Utiles.log_D("dndnnnndndn", viewModel.page)
                if (!it.isNullOrEmpty()) {
                    adapter_.list = it as MutableList<BlogsModel>
                    isLoadingData = false
                }
            }
        }
    }

    override fun setBindingView(inflater: LayoutInflater): FragmentBlogsBinding {
        return FragmentBlogsBinding.inflate(inflater)
    }

    override fun showLoader() {
        binding.loader.bar.visibility = View.VISIBLE
    }

    override fun hideLoader() {
        binding.loader.bar.visibility = View.GONE
    }
}