package com.example.postsapplication.network

import com.latifapp.latif.data.models.BlogsModel
import retrofit2.http.GET


interface NetworkApis {
    @GET("api/public/blogs")
    suspend fun getBlogs():List<BlogsModel>

    @GET("api/public/category")
    suspend fun getCategoryList():List<BlogsModel>
}