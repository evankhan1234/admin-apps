package com.evan.admin.ui.home.newsfeed.ownpost

import com.evan.admin.data.db.entities.Post


interface IOwnPostUpdatedListener {
    fun onUpdate(post: Post)
}