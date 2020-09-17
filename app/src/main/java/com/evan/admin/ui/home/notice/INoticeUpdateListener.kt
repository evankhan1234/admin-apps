package com.evan.admin.ui.home.notice

import com.evan.admin.data.db.entities.Notice


interface INoticeUpdateListener {
    fun onUpdate(notice: Notice)

}