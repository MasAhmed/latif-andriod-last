package com.latifapp.latif.network.repo

import com.latifapp.latif.data.models.*
import com.latifapp.latif.network.ResultWrapper

interface DataRepo {
    suspend fun getBlogsList(page:Int): ResultWrapper<ResponseModel<List<BlogsModel>>>
    suspend fun getBlogsCategoryList(): ResultWrapper<List<CategoryModel>>
    suspend fun getAdsTypeList(): ResultWrapper<ResponseModel<List<AdsTypeModel>>>
    suspend fun getCreateForm(type: String): ResultWrapper<SellFormModel>
   suspend fun saveForm(url: String, model: SaveformModelRequest): ResultWrapper<ResponseModel<SellFormModel>>
}