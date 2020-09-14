package com.evan.admin.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Customer (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("MobileNumber")
    var MobileNumber: String?,
    @SerializedName("Name")
    var Name: String?,
    @SerializedName("Status")
    var Status: Int?,
    @SerializedName("Created")
    var Created: String?,
    @SerializedName("Picture")
    var Picture: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("Gender")
    var Gender: Int?
):Parcelable