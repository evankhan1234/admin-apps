package com.evan.admin.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class FirebaseToken (
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("UserId")
    var UserId: String?,
    @SerializedName("Token")
    var Token: String?,
    @SerializedName("Type")
    var Type: Int?
): Parcelable