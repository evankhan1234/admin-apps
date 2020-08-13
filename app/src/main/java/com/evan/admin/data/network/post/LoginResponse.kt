package com.evan.admin.data.network.post

import com.evan.admin.data.db.entities.User

data class LoginResponse(
    val status: Int?,
    val success: Boolean?,
    val jwt: String?,
    val message: String?,
    val data: User?
)