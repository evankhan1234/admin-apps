package com.evan.admin.ui.home.store.products

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.paging.PageKeyedDataSource
import com.evan.admin.data.db.entities.Product
import com.evan.admin.data.network.post.ProductPost
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.util.*
import com.google.gson.Gson

class ProductDataSource (val context: Context, val alertRepository: HomeRepository) :
    PageKeyedDataSource<Int, Product>() {

    var networkState: MutableLiveData<NetworkState> = MutableLiveData()
    var limitPost: ProductPost? = null
    var categoryId=0
    override fun loadInitial(
        params: LoadInitialParams<Int>,
        callback: LoadInitialCallback<Int, Product>
    ) {
        Coroutines.main {
            networkState.postValue(NetworkState.DONE)
            categoryId=ProductsFragment.currentCategoryId
            try {
                networkState.postValue(NetworkState.LOADING)
                limitPost = ProductPost(categoryId,10, 1)
                val response = alertRepository.getProductList(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,limitPost!!)
                Log.e("response","response"+ Gson().toJson(response))
                response.success.let { isSuccessful ->
                    if (isSuccessful!!) {
                        networkState.postValue(NetworkState.DONE)
                        val nextPageKey = 2
                        callback.onResult(response.data!!, null, nextPageKey)
                        return@main
                    } else {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response?.message!!
                            )
                        )
                    }
                }
            } catch (e: ApiException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage))
            } catch (e: NoInternetException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage))
            }
        }
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
        Coroutines.main {
            try {
                networkState.postValue(NetworkState.LOADING)
                limitPost = ProductPost(categoryId,10, params.key)
                val response =
                    alertRepository.getProductList(SharedPreferenceUtil.getShared(context!!, SharedPreferenceUtil.TYPE_AUTH_TOKEN)!!,limitPost!!)
                response.success.let { isSuccessful ->
                    if (isSuccessful!!) {
                        val nextPageKey =
                            params.key + 1
                        networkState.postValue(NetworkState.DONE)
                        callback.onResult(response.data!!, nextPageKey)
                        return@main
                    } else {
                        networkState.postValue(
                            NetworkState(
                                NetworkState.Status.FAILED,
                                response?.message!!
                            )
                        )

                    }
                }
            } catch (e: ApiException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage!!))
            } catch (e: NoInternetException) {
                networkState.postValue(NetworkState(NetworkState.Status.FAILED, e.localizedMessage!!))
            }
        }
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Product>) {
    }


}