package com.evan.admin.ui.home.order.sourcefactory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.admin.data.db.entities.Order
import com.evan.admin.ui.home.order.source.ProcessingDataSource

class ProcessingOrderSourceFactory (private var dataSource: ProcessingDataSource) :
    DataSource.Factory<Int, Order>() {

    val mutableLiveData: MutableLiveData<ProcessingDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Order> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}