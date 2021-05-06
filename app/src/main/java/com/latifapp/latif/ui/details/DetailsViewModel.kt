package com.latifapp.latif.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.latifapp.latif.data.local.AppPrefsStorage
import com.latifapp.latif.data.models.AdsModel
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.repo.DataRepo
import com.latifapp.latif.ui.base.BaseViewModel
import com.latifapp.latif.ui.main.pets.PetsFragment
import com.latifapp.latif.utiles.Utiles
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsViewModel @Inject constructor(val repo: DataRepo, appPrefsStorage: AppPrefsStorage) :
    BaseViewModel(appPrefsStorage) {

        fun getAdDetails(id:Int?) : LiveData<AdsModel> {
            val liveData =MutableLiveData<AdsModel>(null)
             loader.value = true
            viewModelScope.launch(Dispatchers.IO) {
                val result = repo.getAdDetails(id)
                when (result) {
                    is ResultWrapper.Success -> {
                        withContext(Dispatchers.Main) {
                            liveData.value = (result.value.response.data)
                        }
                    }
                    else -> getErrorMsg(result)
                }
                loader.value = false
            }
             return liveData
        }


}