package com.evan.admin.ui.home.order.modelfactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.order.sourcefactory.ProcessingOrderSourceFactory
import com.evan.admin.ui.home.order.viewmodel.ProcessingOrderViewModel

class ProcessingOrderModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ProcessingOrderSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProcessingOrderViewModel(repository,sourceFactory) as T
    }
}