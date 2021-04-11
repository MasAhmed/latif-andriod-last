package com.latifapp.latif.ui.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.data.models.ResponseModel
import com.latifapp.latif.data.models.SaveformModelRequest
import com.latifapp.latif.data.models.SellFormModel
import com.latifapp.latif.data.models.UserAds
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.CategoriesViewModel
import com.latifapp.latif.utiles.Utiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FilterViewModel @Inject constructor(repo: DataRepo, appPrefsStorage: AppPrefsStorage) :
    CategoriesViewModel(appPrefsStorage,repo) {
    private var adType = ""

    fun getCreateForm(type: String): StateFlow<SellFormModel> {
        adType = type
        val flow_ = MutableStateFlow<SellFormModel>(SellFormModel())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.createFilterForm(type)
            Utiles.log_D("dndnndnddnndnd", " $result")
            when (result) {
                is ResultWrapper.Success -> if (result.value.response != null)
                    flow_.value = result.value.response?.data!!
                else getErrorMsg(result)
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }

    fun saveForm(
        url: String,
        hashMap: MutableMap<String, Any>
    ): LiveData<ResponseModel<SellFormModel>> {
        val list = mutableListOf<UserAds>()
        for (model in hashMap)
            list.add(UserAds(model.key, model.value))

        list.add(UserAds("created_by", "1"))
        val model = SaveformModelRequest(adType, list)
        val flow_ = MutableLiveData<ResponseModel<SellFormModel>>()
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.saveForm(url, model)
            when (result) {
                is ResultWrapper.Success -> {
                    if (result.value.success!!) {
                        withContext(Dispatchers.Main) {
                            flow_.value = result.value
                        }
                    } else {
                        result.value.msg?.let { getErrorMsgString(it) }
                    }
                }
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }
}