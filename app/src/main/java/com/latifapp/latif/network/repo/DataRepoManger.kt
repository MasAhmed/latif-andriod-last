package com.latifapp.latif.network.repo

import com.example.postsapplication.network.NetworkApis
import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.safeApiCall
import javax.inject.Inject

class DataRepoManger @Inject constructor(val apis: NetworkApis) :DataRepo {
    override suspend fun getBlogsList(): ResultWrapper<List<BlogsModel>> {
        return safeApiCall { apis.getBlogs() }
    }
}