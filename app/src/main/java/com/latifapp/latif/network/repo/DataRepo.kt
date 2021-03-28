package com.latifapp.latif.network.repo

import com.latifapp.latif.data.models.AdsTypeModel
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.data.models.ResponseModel
import com.latifapp.latif.data.models.SellFormModel
import com.latifapp.latif.network.ResultWrapper

interface DataRepo {
    suspend fun getBlogsList(): ResultWrapper<ResponseModel<List<BlogsModel>>>
    suspend fun getAdsTypeList(): ResultWrapper<ResponseModel<List<AdsTypeModel>>>
    suspend fun getCreateForm(type: String): ResultWrapper<SellFormModel>
}