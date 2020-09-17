package com.evan.admin.ui.home.order.details

import com.evan.admin.data.db.entities.OrderDetails

interface IOrderDetailsListener {
    fun order(data:MutableList<OrderDetails>?)
    fun onStarted()
    fun onEnd()
}