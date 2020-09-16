package com.evan.admin.ui.home.notice

interface INoticeCreateListener {
    fun onStarted()
    fun onEnd()
    fun onSuccess(message:String)
    fun onFailure(message:String)
}