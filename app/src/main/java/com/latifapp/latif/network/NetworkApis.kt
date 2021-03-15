package com.example.postsapplication.network

import com.example.postsapplication.models.CommentModel
import com.example.postsapplication.models.PostModel
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface NetworkApis {

    @GET("posts")
    suspend fun getPostsList(): List<PostModel>

    @GET("posts/{id}")
    suspend fun getPost(@Path("id") id: Int): PostModel

    @GET("posts/{id}/comments")
    suspend fun getCommentsList(@Path("id") id: Int): List<CommentModel>
}