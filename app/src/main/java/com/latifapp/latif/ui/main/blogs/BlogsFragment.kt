package com.latifapp.latif.ui.main.blogs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.databinding.FragmentBlogsBinding
import com.latifapp.latif.ui.base.BaseFragment
import com.latifapp.latif.ui.main.home.MainActivity
import com.latifapp.latif.ui.main.pets.PetsAdapter
import com.latifapp.latif.utiles.Utiles
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@AndroidEntryPoint
class BlogsFragment : BaseFragment<BlogsViewModel, FragmentBlogsBinding>(),
     PetsCategoryAdapter.CategoryActions {
    private var isLoadingData: Boolean = true

    @Inject
    lateinit var adapter_: BlogsAdapter
    private val petsAdapter = PetsCategoryAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setSearchView()

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

    private fun setSearchView() {
        (activity as MainActivity).searchview.apply {
            setOnCloseListener(SearchView.OnCloseListener {
                return@OnCloseListener false
            })
            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return false
                }
                override fun onQueryTextChange(newText: String?): Boolean {
                    adapter_.list.clear()
                    adapter_.notifyDataSetChanged()
                    if (newText.isNullOrEmpty())
                        getBlogs()
                    else getSearchBlogs(newText)
                    return false
                }

            })

        }
    }

    private fun getSearchBlogs(newText: String) {
        isLoadingData = true // to prevent scroll
        lifecycleScope.launchWhenStarted {
            viewModel.getSearchBlogs(newText).collect {
                adapter_.list.clear()
                if (!it.isNullOrEmpty()) {
                    adapter_.list = it as MutableList<BlogsModel>
                }
            }
        }
    }

    override fun selectedCategory(id: Int) {
        if (id == -1) {
            //get all blogs
         //   getBlogs()
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