package com.evan.admin.ui.home.newsfeed.publicpost.reply

interface IReplyPostListener {
    fun onStarted()
    fun onSuccess(message:String)
    fun onFailure(message:String)
    fun onEnd()
}