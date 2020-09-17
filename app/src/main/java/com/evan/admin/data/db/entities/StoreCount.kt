package com.evan.admin.data.db.entities

import com.google.gson.annotations.SerializedName

class StoreCount (
    @SerializedName("Product")
    var Product: Int?,
    @SerializedName("Customer")
    var Customer: Int?
)