package com.evan.admin.ui.home.store.products

interface ICreateProductListener {
    fun show(value:String)
    fun failure(value:String)
    fun started()
    fun end()
}