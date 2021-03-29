package com.latifapp.latif.data.models

data class SellFormModel(val url: String?=null,
                     val form:List<RequireModel>?=null)
data class RequireModel(
    val type: String?,
    val required: Boolean?,
    val multi: Boolean=false,
    val name: String?,
    val label: String?,
    val options: List<String>?=null

)
data class SaveformModelRequest(
    val type: String?,
    val userAds: List<UserAds>

)
data class UserAds( val id: String?, val value: String?)