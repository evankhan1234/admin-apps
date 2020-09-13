package com.evan.admin.data.network.responses

import com.evan.admin.data.db.entities.Customer
import com.evan.admin.data.db.entities.Product
import com.google.gson.annotations.SerializedName

class CustomerListResponses  (
    @SerializedName("success")
    val success : Boolean?,
    @SerializedName("message")
    val message: String?,
    @SerializedName("status")
    val status: Int?,
    @SerializedName("data")
    val data: MutableList<Customer>?
)