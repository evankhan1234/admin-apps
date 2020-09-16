package com.evan.admin.data.network.responses

import com.evan.admin.data.db.entities.StoreCount
import com.google.gson.annotations.SerializedName

class StoreCountResponses (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: StoreCount?

)