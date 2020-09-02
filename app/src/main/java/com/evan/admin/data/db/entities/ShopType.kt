package com.evan.admin.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ShopType (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Created")
    var Created: String?
): Parcelable