package com.evan.admin.ui.home.store.inactive_shop

import com.evan.admin.data.db.entities.Shop

interface IShopListListener {
    fun shop(data:MutableList<Shop>?)
    fun onStarted()
    fun onEnd()
    fun onFailure(message:String)
}