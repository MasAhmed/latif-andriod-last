package com.latifapp.latif.ui.main.blogs

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.models.BlogsModel
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
    private val flow_ = MutableStateFlow<List<BlogsModel>>(arrayListOf())

     fun getListOfBlogs(): StateFlow<List<BlogsModel>> {
        loader.value=true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getBlogsList()
            when (result) {
                is ResultWrapper.Success -> flow_.value = result.value.response.data!!
                else -> getErrorMsg(result)
            }
            loader.value=false
        }
        return flow_
    }


}