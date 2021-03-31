package com.example.postsapplication.network

import com.latifapp.latif.data.models.*
import retrofit2.http.*


interface NetworkApis {
    @GET("api/public/blogs")
    suspend fun getBlogs(@Query("page") page:Int): ResponseModel<List<BlogsModel>>
    @GET("api/public/blogs/keyword={keyword}")
    suspend fun getSearchBlogs(@Path("keyword") txt:String): ResponseModel<List<BlogsModel>>

    @GET("api/public/blogCategory")
    suspend fun getBlogsCategoryList(): List<CategoryModel>

    @GET("api/public/category")
    suspend fun getCategoryList(): ResponseModel<List<BlogsModel>>

    @GET("api/public/ads-type/list")
    suspend fun getAdsTypeList(): ResponseModel<List<AdsTypeModel>>

    @GET("api/public/ads/get-create-form")
    suspend fun getCreateForm(@Query("adType" )type:String): SellFormModel
    @POST
    suspend fun saveForm(@Url url: String, @Body model: SaveformModelRequest) :ResponseModel<SellFormModel>
}