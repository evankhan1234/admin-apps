package com.evan.admin.ui.home.store.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository


class CustomerModelFactory (
    private val repository: HomeRepository, private val sourceFactory: CustomerSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CustomerViewModel(repository,sourceFactory) as T
    }
}