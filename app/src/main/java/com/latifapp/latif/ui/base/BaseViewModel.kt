package com.latifapp.latif.ui.base

import androidx.lifecycle.ViewModel
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.network.ResultWrapper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

open class BaseViewModel(val lang: String) : ViewModel() {
    private val networkErrorMsgEn = "No Connection !!"
    private val networkErrorMsgAr = "لا يوجد اتصال بالشبكة !!"
    protected var errorMsg = MutableStateFlow<String>("")
    public val errorMsg_: StateFlow<String>
        get() = errorMsg

    protected var loader = MutableStateFlow<Boolean>(false)
    public val loader_: StateFlow<Boolean>
        get() = loader


    protected fun getErrorMsg(result: ResultWrapper<List<BlogsModel>>) {
        when (result) {
            is ResultWrapper.NetworkError -> errorMsg.value =
                if (lang.equals("en")) networkErrorMsgEn else networkErrorMsgAr
            is ResultWrapper.GenericError -> errorMsg.value = result.error!!.message
        }
    }
}