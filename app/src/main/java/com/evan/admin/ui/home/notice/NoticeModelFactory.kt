package com.evan.admin.ui.home.notice

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.evan.admin.data.repositories.HomeRepository


class NoticeModelFactory (
    private val repository: HomeRepository, private val sourceFactory: NoticeSourceFactory
) : ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NoticeViewModel(repository,sourceFactory) as T
    }
}