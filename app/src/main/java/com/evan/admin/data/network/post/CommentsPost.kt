package com.evan.admin.data.network.post

import com.google.gson.annotations.SerializedName

class CommentsPost (
    @SerializedName("PostId")
    val PostId: Int?,
    @SerializedName("Type")
    val Type: Int?
)