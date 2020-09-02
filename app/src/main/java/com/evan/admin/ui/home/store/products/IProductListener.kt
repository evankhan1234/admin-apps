package com.evan.admin.ui.home.store.products

import com.evan.admin.data.db.entities.Product

interface IProductListener {
    fun show(data:MutableList<Product>)
    fun started()
    fun end()
    fun exit()
}