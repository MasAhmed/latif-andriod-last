package com.latifapp.latif.data.models

data class SellModel(val url: String?,
                     val list:List<RequireModel>)
data class RequireModel(
    val type: String?,
    val required: Boolean?,
    val name: String?,
    val label: String?,
    val options: List<String>?=null

)
