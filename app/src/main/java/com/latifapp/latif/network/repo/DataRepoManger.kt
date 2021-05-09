package com.latifapp.latif.network.repo

import com.example.postsapplication.network.NetworkApis
import com.latifapp.latif.data.models.*
import com.latifapp.latif.network.ResultWrapper
import com.latifapp.latif.network.safeApiCall
import javax.inject.Inject

class DataRepoManger @Inject constructor(val apis: NetworkApis) : DataRepo {
    override suspend fun getBlogsList(page: Int,category: Int?): ResultWrapper<ResponseModel<List<BlogsModel>>> {
        return safeApiCall { apis.getBlogs(page,category) }
    }

    override suspend fun getSearchBlogs(txt: String,page: Int): ResultWrapper<ResponseModel<List<BlogsModel>>> {
        return safeApiCall { apis.getSearchBlogs(txt,page) }
    }

    override suspend fun getBlogsCategoryList(): ResultWrapper<ResponseModel<List<CategoryItemsModel>>> {
        return safeApiCall { apis.getBlogsCategoryList() }
    }

    override suspend fun getDetailsOfBlog(id: Int?): ResultWrapper<ResponseModel<BlogsModel>> {
        return safeApiCall { apis.getBlogDetails(id) }
    }

    override suspend fun getCategoriesList(type:Int): ResultWrapper<ResponseModel<List<CategoryModel>>> {
        return safeApiCall { apis.getCatsTypeList(type) }
    }

    override suspend fun getCategoriesListFromUrl(url: String): ResultWrapper<ResponseModel<List<CategoryModel>>> {

        return safeApiCall { apis.getCatsTypeListUrl(url) }
    }

    override suspend fun getAdsTypeList(): ResultWrapper<ResponseModel<List<AdsTypeModel>>> {
        return safeApiCall { apis.getAdsTypeList() }

    }

    override suspend fun getCreateForm(type: String): ResultWrapper<ResponseModel<SellFormModel>> {
        return safeApiCall { apis.getCreateForm(type) }
    }

    override suspend fun createFilterForm(type: String): ResultWrapper<ResponseModel<SellFormModel>> {
        return safeApiCall { apis.createFilterForm(type) }
    }

    override suspend fun getNearestAds(type: String?,lat: Double,lag: Double,category: Int?,page: Int): ResultWrapper<ResponseModel<List<AdsModel>>> {
        return safeApiCall { apis.getNearestAds(type=type,longitude=lag,
            latitude = lat,category=category,page=page) }
    }

    override suspend fun getAdDetails(id: Int?): ResultWrapper<ResponseModel<AdsModel>> {
        return safeApiCall { apis.getAdDetails(id) }
    }

    override suspend fun createBlog(createBlogsModel: CreateBlogsModel): ResultWrapper<ResponseModel<BlogsModel>> {
        return safeApiCall { apis.createBlog(createBlogsModel) }
    }

    override suspend fun saveForm(
        url: String,
        model: SaveformModelRequest
    ): ResultWrapper<ResponseModel<SellFormModel>> {

        return safeApiCall { apis.saveForm("$url", model) }
    }

    override suspend fun getSubscribeList(page: Int): ResultWrapper<ResponseModel<List<SubscribeModel>>> {
        return safeApiCall { apis.getSubscribeList(page) }
    }

    override suspend fun saveFilter(
        url: String,
        model: SaveformModelRequest
    ): ResultWrapper<ResponseModel<List<AdsModel>>> {
        return safeApiCall { apis.saveFilter("$url", model) }
    }
}