package com.evan.admin.ui.home.order.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.order.sourcefactory.PendingOrderSourceFactory
import com.evan.admin.ui.home.order.viewmodel.PendingOrderViewModel

class PendingOrderModelFactory (
    private val repository: HomeRepository, private val sourceFactory: PendingOrderSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PendingOrderViewModel(repository,sourceFactory) as T
    }
}