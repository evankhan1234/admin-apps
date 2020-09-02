package com.evan.admin.ui.home.store.products

import androidx.lifecycle.MutableLiveData
import androidx.paging.DataSource
import com.evan.admin.data.db.entities.Product

class ProductSourceFactory (private var dataSource: ProductDataSource) :
    DataSource.Factory<Int, Product>() {

    val mutableLiveData: MutableLiveData<ProductDataSource> = MutableLiveData()
    override fun create(): DataSource<Int, Product> {
        mutableLiveData.postValue(dataSource)
        return dataSource
    }
}