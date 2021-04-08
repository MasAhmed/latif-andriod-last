package com.latifapp.latif.data.models


data class BlogsModel(
    val id: Int?,
    val title: String?,
    val category: String?,
    val description: String?,
    val image: String?,
    val path: String?,
    val createdDate: String?,
    val user: UserModel?,
)

data class UserModel(val id: Int?,val email: String?,val firstName: String?,val lastName: String?,val address: String?,val phone: String?)