package com.evan.admin.ui.auth.interfaces

import com.evan.admin.data.db.entities.StoreCount

interface IStoreCountListener {
    fun onStore(store: StoreCount)
}