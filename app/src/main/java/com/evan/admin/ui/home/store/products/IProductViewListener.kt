package com.evan.admin.ui.home.store.products

import com.evan.admin.data.db.entities.Product

interface IProductViewListener {
    fun onUpdate(product: Product)
}