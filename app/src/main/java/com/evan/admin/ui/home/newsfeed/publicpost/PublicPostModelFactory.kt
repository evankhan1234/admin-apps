package com.evan.admin.ui.home.newsfeed.publicpost

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository


class PublicPostModelFactory (
    private val repository: HomeRepository, private val sourceFactory: PublicPostSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return PublicPostViewModel(repository,sourceFactory) as T
    }
}