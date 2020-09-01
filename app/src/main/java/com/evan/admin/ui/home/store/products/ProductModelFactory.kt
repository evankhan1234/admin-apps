package com.evan.admin.ui.home.store.products

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.store.post.PostSourceFactory
import com.evan.admin.ui.home.store.post.PostViewModel

class ProductModelFactory (
    private val repository: HomeRepository, private val sourceFactory: ProductSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProductViewModel(repository,sourceFactory) as T
    }
}