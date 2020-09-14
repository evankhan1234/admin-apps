package com.evan.admin.ui.home.store.customer

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.admin.data.db.entities.Customer
import com.evan.admin.data.network.post.CustomerUpdatePost
import com.evan.admin.data.network.post.IDPost
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.util.ApiException
import com.evan.admin.util.Coroutines
import com.evan.admin.util.NetworkState
import com.evan.admin.util.NoInternetException
import com.google.gson.Gson

class CustomerViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: CustomerSourceFactory
) : ViewModel() {


    var customerUpdatePost: CustomerUpdatePost?= null
    var customerSuccessListener: ICustomerSuccessListener?= null
    var listOfAlerts: LiveData<PagedList<Customer>>? = null
    private val pageSize = 7

    init {
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(true)
            .build()
        listOfAlerts = LivePagedListBuilder(alertListSourceFactory, config).build()
    }

    fun replaceSubscription(lifecycleOwner: LifecycleOwner) {
        listOfAlerts?.removeObservers(lifecycleOwner)
        val config = PagedList.Config.Builder()
            .setPageSize(pageSize)
            .setInitialLoadSizeHint(pageSize)
            .setEnablePlaceholders(false)
            .build()
        listOfAlerts =
            LivePagedListBuilder(alertListSourceFactory, config).build()
    }

    fun getNetworkState(): LiveData<NetworkState> =
        Transformations.switchMap<CustomerDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            CustomerDataSource::networkState
        )

    fun updateCustomer(header:String,id:Int,status:Int) {
        customerSuccessListener?.onStarted()
        Coroutines.main {
            try {
                customerUpdatePost= CustomerUpdatePost(id,status)
                Log.e("createToken", "createToken" + Gson().toJson(customerUpdatePost))
                val response = repository.updateCustomer(header,customerUpdatePost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                if (response.success!!){
                    customerSuccessListener?.onSuccess(response?.message!!)
                }
                else{
                    customerSuccessListener?.onFailure(response?.message!!)
                }
                customerSuccessListener?.onEnd()
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
                customerSuccessListener?.onFailure(e?.message!!)
                customerSuccessListener?.onEnd()
            } catch (e: NoInternetException) {
                customerSuccessListener?.onEnd()
                customerSuccessListener?.onFailure(e?.message!!)
            }
        }

    }


}