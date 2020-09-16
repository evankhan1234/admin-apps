package com.evan.admin.ui.home.newsfeed.publicpost.comments

import com.evan.admin.data.db.entities.Comments


interface ICommentsUpdateListener {
    fun onShow(comments: Comments)
}