package com.latifapp.latif.ui.sell

import android.util.Log
import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.models.AdsTypeModel
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.data.models.SellFormModel
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.BaseViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SellViewModel @Inject constructor(val repo: DataRepo):BaseViewModel("en") {

    private val flow_ = MutableStateFlow<List<AdsTypeModel>>(arrayListOf())

    fun getAdsTypeList(): StateFlow<List<AdsTypeModel>> {
        loader.value=true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAdsTypeList()
            Log.d("dndnndnddnndnd"," $result")
            when (result) {
                is ResultWrapper.Success -> flow_.value = result.value.response.data!!
                else -> getErrorMsg(result)
            }
            loader.value=false
        }
        return flow_
    }

    fun getCreateForm(type:String): StateFlow<SellFormModel> {
        val flow_ = MutableStateFlow<SellFormModel>(SellFormModel())
        loader.value=true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getCreateForm(type)
            Log.d("dndnndnddnndnd"," $result")
            when (result) {
                is ResultWrapper.Success -> flow_.value = result.value
                else -> getErrorMsg(result)
            }
            loader.value=false
        }
        return flow_
    }
}