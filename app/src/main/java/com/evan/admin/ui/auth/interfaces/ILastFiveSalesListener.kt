package com.evan.admin.ui.auth.interfaces

import com.evan.admin.data.db.entities.LastFiveSales


interface ILastFiveSalesListener {

    fun onLast(data: MutableList<LastFiveSales>)
}