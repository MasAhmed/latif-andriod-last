package com.latifapp.latif.ui.base

import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.data.models.CategoryModel
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.utiles.Utiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class CategoriesViewModel(appPrefsStorage: AppPrefsStorage, val repo:DataRepo):BaseViewModel(appPrefsStorage) {

    fun getCategoriesList(type:Int):StateFlow<List<CategoryModel>>{
        val flow_= MutableStateFlow<List<CategoryModel>>(arrayListOf())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getCategoriesList(type)
            Utiles.log_D("dndnndnddnndnd", " $result")
            when (result) {
                is ResultWrapper.Success -> flow_.value = result.value.response.data!!
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }
}