package com.evan.admin.ui.home.order.sourcefactory

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.admin.data.db.entities.Order
import com.evan.admin.ui.home.order.source.DeliveredDataSource

class DeliveredOrderSourceFactory (private var dataSource: DeliveredDataSource) :
    DataSource.Factory<Int, Order>() {

    val mutableLiveData: MutableLiveData<DeliveredDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Order> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}