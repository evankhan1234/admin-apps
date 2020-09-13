package com.evan.admin.data.network.post

import com.google.gson.annotations.SerializedName

class SystemProductPost(
    @SerializedName("ItemName")
    var ItemName: String?,
    @SerializedName("Description")
    var Description: String?,
    @SerializedName("ItemCode")
    var ItemCode: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("UnitId")
    var UnitId: Int?,
    @SerializedName("SalesPrice")
    var SalesPrice: Double?,
    @SerializedName("PurchasePrice")
    var PurchasePrice: Double?,
    @SerializedName("ShopType")
    var ShopType: Int?,
    @SerializedName("Stock")
    var Stock: Int?,
    @SerializedName("Quantity")
    var Quantity: Int?,
    @SerializedName("Discount")
    var Discount: Double?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Status")
    var Status: Int?
)