package com.evan.admin.ui.home.store.products

import com.evan.admin.data.db.entities.ShopType


interface ShopTypeInterface {
    fun shopType(shop:MutableList<ShopType>?)
}