package com.evan.admin.ui.home.notice

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.admin.data.db.entities.Notice


class NoticeSourceFactory (private var dataSource: NoticeDataSource) :
    DataSource.Factory<Int, Notice>() {

    val mutableLiveData: MutableLiveData<NoticeDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Notice> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}