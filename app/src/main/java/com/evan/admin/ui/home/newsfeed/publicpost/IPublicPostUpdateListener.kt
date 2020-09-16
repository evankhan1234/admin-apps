package com.evan.admin.ui.home.newsfeed.publicpost

import com.evan.admin.data.db.entities.Post

interface IPublicPostUpdateListener {
    fun onUpdate(post: Post)
}