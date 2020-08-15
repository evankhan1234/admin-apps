package com.evan.admin.ui.home.store.inactive_shop

import com.evan.admin.data.db.entities.Shop

interface IShopUpdateListener {
    fun onUpdate(shop: Shop)
}