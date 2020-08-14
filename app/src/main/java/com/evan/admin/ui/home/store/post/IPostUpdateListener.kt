package com.evan.admin.ui.home.store.post

import com.evan.admin.data.db.entities.Post

interface IPostUpdateListener {
   fun onUpdate(post:Post)
}