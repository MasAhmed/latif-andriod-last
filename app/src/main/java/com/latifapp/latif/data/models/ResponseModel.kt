package com.latifapp.latif.data.models

data class ResponseModel<T>(val msg:String?,val success:Boolean?,val response:ResponseData<T>) {

}
data class ResponseData<T>(val data:T?) {
}