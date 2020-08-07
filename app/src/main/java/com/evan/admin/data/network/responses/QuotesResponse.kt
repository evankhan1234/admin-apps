package com.evan.admin.data.network.responses

import com.evan.admin.data.db.entities.Quote


data class QuotesResponse (
    val isSuccessful: Boolean,
    val quotes: List<Quote>
)