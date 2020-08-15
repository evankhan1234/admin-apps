package com.evan.admin.data.db.entities

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
class Shop(
    @SerializedName("Id")
    var Id: Int?,
    @SerializedName("ShopName")
    var ShopName: String?,
    @SerializedName("Address")
    var Address: String?,
    @SerializedName("LicenseNumber")
    var LicenseNumber: String?,
    @SerializedName("Email")
    var Email: String?,
    @SerializedName("AgreementDate")
    var AgreementDate: String?,
    @SerializedName("OwnerName")
    var OwnerName: String?,
    @SerializedName("OwnerMobileNumber")
    var OwnerMobileNumber: String?,
    @SerializedName("Picture")
    var Picture: String?
): Parcelable