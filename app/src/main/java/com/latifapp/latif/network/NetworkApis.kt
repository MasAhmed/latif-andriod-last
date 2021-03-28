package com.example.postsapplication.network

import com.latifapp.latif.data.models.*
import retrofit2.http.Body
import retrofit2.http.GET


interface NetworkApis {
    @GET("api/public/blogs")
    suspend fun getBlogs(): ResponseModel<List<BlogsModel>>

    @GET("api/public/category")
    suspend fun getCategoryList(): ResponseModel<List<BlogsModel>>

    @GET("api/public/ads-type/list")
    suspend fun getAdsTypeList(): ResponseModel<List<AdsTypeModel>>

    @GET("api/public/ads/get-create-form")
    suspend fun getCreateForm(): SellFormModel
}