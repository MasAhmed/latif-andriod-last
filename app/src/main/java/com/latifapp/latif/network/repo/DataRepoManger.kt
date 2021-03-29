package com.latifapp.latif.network.repo

import com.example.postsapplication.network.NetworkApis
import com.latifapp.latif.data.models.*
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.safeApiCall
import javax.inject.Inject

class DataRepoManger @Inject constructor(val apis: NetworkApis) : DataRepo {
    override suspend fun getBlogsList(): ResultWrapper<ResponseModel<List<BlogsModel>>> {
        return safeApiCall { apis.getBlogs() }
    }

    override suspend fun getAdsTypeList(): ResultWrapper<ResponseModel<List<AdsTypeModel>>> {
        return safeApiCall { apis.getAdsTypeList() }

    }

    override suspend fun getCreateForm(type: String): ResultWrapper<SellFormModel> {
        return safeApiCall { apis.getCreateForm(type) }
    }

    override suspend fun saveForm(url: String, model: SaveformModelRequest): ResultWrapper<SellFormModel> {
        return safeApiCall { apis.saveForm(url,model) }
    }
}