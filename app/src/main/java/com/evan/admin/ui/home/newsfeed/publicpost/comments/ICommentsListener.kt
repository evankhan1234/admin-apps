package com.evan.admin.ui.home.newsfeed.publicpost.comments

import com.evan.admin.data.db.entities.Comments


interface ICommentsListener {
    fun load(data:MutableList<Comments>?)
    fun onStarted()
    fun onEnd()
}