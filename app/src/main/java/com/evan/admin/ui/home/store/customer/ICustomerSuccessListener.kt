package com.evan.admin.ui.home.store.customer

interface ICustomerSuccessListener {
    fun onStarted()
    fun onEnd()
    fun onSuccess(message:String)
    fun onFailure(message:String)
}