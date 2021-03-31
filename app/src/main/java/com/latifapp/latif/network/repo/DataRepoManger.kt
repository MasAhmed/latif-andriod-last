package com.latifapp.latif.network.repo

import com.example.postsapplication.network.NetworkApis
import com.latifapp.latif.data.models.*
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.safeApiCall
import com.latifapp.latif.utiles.Utiles
import java.net.URLEncoder
import javax.inject.Inject

class DataRepoManger @Inject constructor(val apis: NetworkApis) : DataRepo {
    override suspend fun getBlogsList(page: Int): ResultWrapper<ResponseModel<List<BlogsModel>>> {
        return safeApiCall { apis.getBlogs(page) }
    }

    override suspend fun getSearchBlogs(txt: String): ResultWrapper<ResponseModel<List<BlogsModel>>> {
        return safeApiCall { apis.getSearchBlogs(txt) }
    }

    override suspend fun getBlogsCategoryList(): ResultWrapper<List<CategoryModel>> {
        return safeApiCall { apis.getBlogsCategoryList() }
    }

    override suspend fun getAdsTypeList(): ResultWrapper<ResponseModel<List<AdsTypeModel>>> {
        return safeApiCall { apis.getAdsTypeList() }

    }

    override suspend fun getCreateForm(type: String): ResultWrapper<SellFormModel> {
        return safeApiCall { apis.getCreateForm(type) }
    }

    override suspend fun saveForm(
        url: String,
        model: SaveformModelRequest
    ): ResultWrapper<ResponseModel<SellFormModel>> {

        return safeApiCall { apis.saveForm("$url", model) }
    }
}