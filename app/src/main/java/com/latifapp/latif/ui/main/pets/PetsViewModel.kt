package com.latifapp.latif.ui.main.pets

import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.data.models.AdsModel
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.CategoriesViewModel
import com.latifapp.latif.utiles.Utiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PetsViewModel @Inject constructor(appPrefsStorage: AppPrefsStorage, repo: DataRepo) :
    CategoriesViewModel(
        appPrefsStorage, repo
    ) {

    var page = 0
    fun getListOfPets(type: String,lat: Double,lag: Double): StateFlow<List<AdsModel>> {
        val flow_ = MutableStateFlow<List<AdsModel>>(arrayListOf())
        loader.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val result = repo.getNearestAds(type,lat, lag, page)
            when (result) {
                is ResultWrapper.Success -> {
                    Utiles.log_D("nvnnvnvnvnnvnv", "${result.value}")
                    flow_.value = result.value.response.data!!
                   // page++
                }
                else -> getErrorMsg(result)
            }
            loader.value = false
        }
        return flow_
    }
}