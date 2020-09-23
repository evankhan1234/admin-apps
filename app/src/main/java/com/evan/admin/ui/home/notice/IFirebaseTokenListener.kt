package com.evan.admin.ui.home.notice

import com.evan.admin.data.db.entities.FirebaseToken
import com.evan.admin.data.db.entities.Unit

interface IFirebaseTokenListener {
    fun unit(shop:MutableList<FirebaseToken>?)
}