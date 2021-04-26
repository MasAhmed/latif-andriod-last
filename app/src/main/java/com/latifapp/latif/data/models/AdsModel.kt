package com.latifapp.latif.data.models

data class AdsTypeModel(val id:Int?,val name:String?,val nameAr:String?)
data class AdsModel(
    val id: Int?,
    val name: String?,
    val nameAr: String?,
    val code: String?,
    val created_at: String?,
    val description: String?,
    val short_description: String?,
    val price: String?,
    val image: String?,
    val images: List<ImagesModel>?,
    val breed: String?,
    val stock: String?,
    val latitude: Double=0.0,
    val longitude: Double=0.0,
    val external_link: Boolean?,
    val createdBy: UserModel?
)

data class ImagesModel(val id:Int,val image:String,val external_link:Boolean)