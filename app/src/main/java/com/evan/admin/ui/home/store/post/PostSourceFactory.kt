package com.evan.admin.ui.home.store.post

import androidx.lifecycle.MutableLiveData
import com.evan.admin.data.db.entities.Post
import androidx.paging.DataSource

class PostSourceFactory  (private var dataSource: PostDataSource) :
    DataSource.Factory<Int, Post>() {

    val mutableLiveData: MutableLiveData<PostDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Post> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}