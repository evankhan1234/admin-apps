package com.evan.admin.ui.home.newsfeed.ownpost

interface IPostListener {
    fun onStarted()
    fun onSuccess(message:String)
    fun onFailure(message:String)
    fun onEnd()
}