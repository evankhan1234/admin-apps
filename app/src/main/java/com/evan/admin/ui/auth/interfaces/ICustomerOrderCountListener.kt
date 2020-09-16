package com.evan.admin.ui.auth.interfaces

import com.evan.admin.data.db.entities.CustomerOrderCount


interface ICustomerOrderCountListener {
    fun onCount(customerOrderCount: CustomerOrderCount)
}