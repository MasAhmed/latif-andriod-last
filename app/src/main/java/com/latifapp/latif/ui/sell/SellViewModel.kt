package com.latifapp.latif.ui.sell

import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.models.*
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.BaseViewModel
import com.latifapp.latif.utiles.Utiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SellViewModel @Inject constructor(val repo: DataRepo) : BaseViewModel("en") {

    private val flow_ = MutableStateFlow<List<AdsTypeModel>>(arrayListOf())
    private var adType = ""

    fun getAdsTypeList(): StateFlow<List<AdsTypeModel>> {
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getAdsTypeList()
            Utiles.log_D("dndnndnddnndnd", " $result")
            when (result) {
                is ResultWrapper.Success -> flow_.value = result.value.response.data!!
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }

    fun getCreateForm(type: String): StateFlow<SellFormModel> {
        adType = type
        val flow_ = MutableStateFlow<SellFormModel>(SellFormModel())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getCreateForm(type)
            Utiles.log_D("dndnndnddnndnd", " $result")
            when (result) {
                is ResultWrapper.Success -> flow_.value = result.value
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }

    fun saveForm(url: String, hashMap: MutableMap<String, String>): StateFlow<SellFormModel> {
        val list = mutableListOf<UserAds>()
        for (model in hashMap)
            list.add(UserAds(model.key, model.value))

        list.add(UserAds("created_by", "1"))
        val model = SaveformModelRequest(adType, list)
        val flow_ = MutableStateFlow<SellFormModel>(SellFormModel())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {

            val result = repo.saveForm(url, model)
            Utiles.log_D("dndnndnddnndnd", " $result")
            when (result) {
                is ResultWrapper.Success -> {
                    if (result.value.success!!) {
                        flow_.value = result.value.response.data!!
                    } else {
                        getErrorMsg(result)
                    }
                } else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }
}