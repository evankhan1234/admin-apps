package com.evan.admin.ui.auth

import com.evan.admin.data.db.entities.User


interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}