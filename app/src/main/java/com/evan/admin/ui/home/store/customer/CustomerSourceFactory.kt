package com.evan.admin.ui.home.store.customer

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.admin.data.db.entities.Customer


class CustomerSourceFactory (private var dataSource: CustomerDataSource) :
    DataSource.Factory<Int, Customer>() {

    val mutableLiveData: MutableLiveData<CustomerDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Customer> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}