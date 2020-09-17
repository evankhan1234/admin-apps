package com.evan.admin.ui.home.order.viewmodel

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.admin.data.db.entities.Order
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.order.source.DeliveredDataSource
import com.evan.admin.ui.home.order.sourcefactory.DeliveredOrderSourceFactory

import com.evan.admin.util.NetworkState

class DeliveredOrderViewModel  (
    val repository: HomeRepository,
    val alertListSourceFactory: DeliveredOrderSourceFactory
) : ViewModel() {


    var listOfAlerts: LiveData<PagedList<Order>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Order>(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Order>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<DeliveredDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            DeliveredDataSource::networkState
        )


}