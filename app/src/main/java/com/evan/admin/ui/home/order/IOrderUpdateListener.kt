package com.evan.admin.ui.home.order

import com.evan.admin.data.db.entities.Order
import com.evan.admin.data.db.entities.Product

interface IOrderUpdateListener {
    fun onUpdate(order: Order)
}