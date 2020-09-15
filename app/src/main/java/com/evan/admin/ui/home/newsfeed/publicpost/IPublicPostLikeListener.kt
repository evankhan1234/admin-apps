package com.evan.admin.ui.home.newsfeed.publicpost

interface IPublicPostLikeListener {
  fun onCount(count:Int?,type:Int,id:Int)
}