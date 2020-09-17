package com.evan.admin.data.network.responses

import com.evan.admin.data.db.entities.Order
import com.evan.admin.data.db.entities.OrderDetails
import com.google.gson.annotations.SerializedName

class OrderDetailsResponse (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<OrderDetails>?
)