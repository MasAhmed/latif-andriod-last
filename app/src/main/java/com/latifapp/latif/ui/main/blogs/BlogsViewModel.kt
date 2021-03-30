package com.latifapp.latif.ui.main.blogs

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.data.models.CategoryModel
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.network.safeApiCall
import com.latifapp.latif.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class BlogsViewModel @Inject constructor(val repo: DataRepo) : BaseViewModel("en") {
    var page = 0
    fun getListOfBlogs(): StateFlow<List<BlogsModel>> {
        val flow_ = MutableStateFlow<List<BlogsModel>>(arrayListOf())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getBlogsList(page)
            when (result) {
                is ResultWrapper.Success -> {
                    flow_.value = result.value.response.data!!
                    page++
                }
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }

    fun getBlogsCategoryList(): StateFlow<List<CategoryModel>> {
        val flow_ = MutableStateFlow<List<CategoryModel>>(arrayListOf())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getBlogsCategoryList()
            when (result) {
                is ResultWrapper.Success -> {
                    flow_.value = result.value!!
                }
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }


}