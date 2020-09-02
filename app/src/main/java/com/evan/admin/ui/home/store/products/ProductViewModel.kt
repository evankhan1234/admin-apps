package com.evan.admin.ui.home.store.products

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.admin.data.db.entities.Product
import com.evan.admin.data.network.post.ProductSearchPost
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.util.ApiException
import com.evan.admin.util.Coroutines
import com.evan.admin.util.NetworkState
import com.evan.admin.util.NoInternetException
import com.google.gson.Gson

class ProductViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: ProductSourceFactory
) : ViewModel() {

    var searchPost: ProductSearchPost?=null
    var listener: IProductListener?=null
    var shopTypeListener: IShopTypeListener?=null
    var listOfAlerts: LiveData<PagedList<Product>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder<Int, Product>(alertListSourceFactory, config).build()
    }
    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder<Int, Product>(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<ProductDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            ProductDataSource::networkState
        )

    fun getSystemList(header:String,keyword:String,shopTypeId:Int) {
        listener?.started()
        Coroutines.main {
            try {
                searchPost= ProductSearchPost(shopTypeId,keyword)
                val response = repository.getProductSearchList(header,searchPost!!)
                listener?.show(response?.data!!)
                listener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                listener?.end()
                listener?.exit()
            } catch (e: NoInternetException) {
                listener?.end()
            }
        }

    }
    fun getShopType() {
        Coroutines.main {
            try {
                val authResponse = repository.getShopType()
                shopTypeListener?.shopType(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
}