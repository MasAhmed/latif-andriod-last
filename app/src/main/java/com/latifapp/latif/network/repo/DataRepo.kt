package com.latifapp.latif.network.repo

import com.latifapp.latif.data.models.BlogsModel
import com.latifapp.latif.network.ResultWrapper

interface DataRepo {
    suspend fun getBlogsList(): ResultWrapper<List<BlogsModel>>
}