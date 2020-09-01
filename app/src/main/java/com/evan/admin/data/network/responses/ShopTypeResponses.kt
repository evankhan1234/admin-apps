package com.evan.admin.data.network.responses

import com.evan.admin.data.db.entities.ShopType

import com.google.gson.annotations.SerializedName

data class ShopTypeResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<ShopType>?

)