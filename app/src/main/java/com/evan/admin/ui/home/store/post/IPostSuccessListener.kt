package com.evan.admin.ui.home.store.post

interface IPostSuccessListener {
    fun onStarted()
    fun onEnd()
    fun onSuccess(message:String)
    fun onFailure(message:String)
}