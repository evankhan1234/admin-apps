package com.evan.admin.ui.home.quotes

import android.util.Log
import androidx.lifecycle.ViewModel;
import com.evan.admin.data.db.entities.Quote
import com.evan.admin.data.repositories.QuotesRepository
import com.evan.admin.util.Coroutines
import com.evan.admin.util.lazyDeferred


class QuotesViewModel(
   private val repository: QuotesRepository
) : ViewModel() {

    val quotes by lazyDeferred {
        repository.getQuotes()
    }
    fun saveUser(quote: Quote){
        Coroutines.main {
            Log.e("sdfds","Sds"+quote.created_at)
            repository.saveUser(quote)
        }

    }
}
