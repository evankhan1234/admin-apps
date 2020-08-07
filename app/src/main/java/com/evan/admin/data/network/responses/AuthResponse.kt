package com.evan.admin.data.network.responses

import com.evan.admin.data.db.entities.User


data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?
)