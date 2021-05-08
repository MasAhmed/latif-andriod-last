package com.latifapp.latif.data.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


data class BlogsModel(
    val id: Int?,
    val title: String?,
    val category: String?,
    val description: String?,
    val image: String?,
    val images: List<String>?,
    val path: String?,
    val createdDate: String?,
    val externalLink: Boolean,
    val user: UserModel?,
)

data class CreateBlogsModel(
    val userId: Int,
    val title: String?,
    val category: Int?,
    val description: String?,
    val images: List<String>?=null,
    val extrnImage: List<String>?,
    val _external: Boolean=true,

)

@Parcelize
data class UserModel(
    val id: Int?,
    val email: String?,
    val firstName: String?,
    val lastName: String?,
    val address: String?,
    val phone: String?,
    val avatar: String?,
    val adsCount: Int?,
    val registrationDate: String?
): Parcelable