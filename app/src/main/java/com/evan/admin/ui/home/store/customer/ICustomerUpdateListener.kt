package com.evan.admin.ui.home.store.customer

import com.evan.admin.data.db.entities.Customer
import com.evan.admin.data.db.entities.Post

interface ICustomerUpdateListener {
    fun onUpdate(customer: Customer)
}