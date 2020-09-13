package com.evan.admin.ui.home.store.products

import com.evan.admin.data.db.entities.Unit


interface IUnitListener {
    fun unit(shop:MutableList<Unit>?)
}