package com.evan.admin.ui.home.store.inactive_shop

interface IShopSuccessListener {
    fun onStarted()
    fun onEnd()
    fun onSuccess(message:String)
    fun onFailure(message:String)
}