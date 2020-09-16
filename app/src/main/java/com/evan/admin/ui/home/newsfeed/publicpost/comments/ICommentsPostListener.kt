package com.evan.admin.ui.home.newsfeed.publicpost.comments

interface ICommentsPostListener {
    fun onStarted()
    fun onSuccess(message:String)
    fun onFailure(message:String)
    fun onEnd()
}