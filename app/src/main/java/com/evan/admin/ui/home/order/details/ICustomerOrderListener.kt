package com.evan.dokan.ui.home.order.details

import com.evan.admin.data.db.entities.CustomerOrder

interface ICustomerOrderListener {
    fun onShow(customerOrder: CustomerOrder?)
    fun onEmpty()
}