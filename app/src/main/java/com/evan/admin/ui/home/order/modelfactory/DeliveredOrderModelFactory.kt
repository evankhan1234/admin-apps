package com.evan.admin.ui.home.order.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.order.sourcefactory.DeliveredOrderSourceFactory
import com.evan.admin.ui.home.order.viewmodel.DeliveredOrderViewModel


class DeliveredOrderModelFactory (
    private val repository: HomeRepository, private val sourceFactory: DeliveredOrderSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return DeliveredOrderViewModel(repository,sourceFactory) as T
    }
}