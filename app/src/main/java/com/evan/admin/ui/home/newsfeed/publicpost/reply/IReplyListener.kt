package com.evan.admin.ui.home.newsfeed.publicpost.reply

import com.evan.admin.data.db.entities.Reply


interface IReplyListener {
    fun load(data:MutableList<Reply>?)
    fun onStarted()
    fun onEnd()
}