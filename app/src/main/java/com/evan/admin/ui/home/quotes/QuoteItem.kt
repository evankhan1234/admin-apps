package com.evan.admin.ui.home.quotes

import com.evan.admin.R
import com.evan.admin.data.db.entities.Quote
import com.evan.admin.databinding.ItemQuoteBinding
import com.xwray.groupie.databinding.BindableItem


class QuoteItem(
    private val quote: Quote
) : BindableItem<ItemQuoteBinding>(){

    override fun getLayout() = R.layout.item_quote

    override fun bind(viewBinding: ItemQuoteBinding, position: Int) {
        viewBinding.setQuote(quote)
    }
}