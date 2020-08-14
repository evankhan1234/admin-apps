package com.evan.admin.ui.home.store.post

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository

class PostModelFactory (
    private val repository: HomeRepository, private val sourceFactory: PostSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PostViewModel(repository,sourceFactory) as T
    }
}