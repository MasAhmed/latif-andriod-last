package com.latifapp.latif.network.repo

import com.latifapp.latif.data.models.*
import com.latifapp.latif.network.ResultWrapper

interface DataRepo {
    suspend fun getBlogsList(page:Int): ResultWrapper<ResponseModel<List<BlogsModel>>>
    suspend fun getSearchBlogs(txt: String):ResultWrapper<ResponseModel<List<BlogsModel>>>
    suspend fun getBlogsCategoryList(): ResultWrapper<ResponseModel<List<CategoryItemsModel>>>
    suspend fun getCategoriesList(type:Int): ResultWrapper<ResponseModel<List<CategoryModel>>>
    suspend fun getCategoriesListFromUrl(url:String): ResultWrapper<ResponseModel<List<CategoryModel>>>
    suspend fun getAdsTypeList(): ResultWrapper<ResponseModel<List<AdsTypeModel>>>
    suspend fun getCreateForm(type: String): ResultWrapper<ResponseModel<SellFormModel>>
    suspend fun createFilterForm(type: String): ResultWrapper<ResponseModel<SellFormModel>>
   suspend fun saveForm(url: String, model: SaveformModelRequest): ResultWrapper<ResponseModel<SellFormModel>>
   suspend fun getNearestAds(type: String,lat: Double,lag: Double,page: Int): ResultWrapper<ResponseModel<List<AdsModel>>>

    }