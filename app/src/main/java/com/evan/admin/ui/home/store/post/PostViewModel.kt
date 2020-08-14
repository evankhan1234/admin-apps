package com.evan.admin.ui.home.store.post

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.evan.admin.data.db.entities.Post
import com.evan.admin.data.network.post.IDPost
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.util.ApiException
import com.evan.admin.util.Coroutines
import com.evan.admin.util.NetworkState
import com.evan.admin.util.NoInternetException
import com.google.gson.Gson

class PostViewModel (
    val repository: HomeRepository,
    val alertListSourceFactory: PostSourceFactory
) : ViewModel() {


    var idPost:IDPost?= null
    var postSuccessListener:IPostSuccessListener?= null
    var listOfAlerts: LiveData<PagedList<Post>>? = null
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
        Transformations.switchMap<PostDataSource, NetworkState>(
            alertListSourceFactory.mutableLiveData,
            PostDataSource::networkState
        )

    fun updatePost(header:String,id:Int) {
        postSuccessListener?.onStarted()
        Coroutines.main {
            try {
                idPost= IDPost(id)
                Log.e("createToken", "createToken" + Gson().toJson(idPost))
                val response = repository.updatePost(header,idPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                if (response.success!!){
                    postSuccessListener?.onSuccess(response?.message!!)
                }
                else{
                    postSuccessListener?.onFailure(response?.message!!)
                }
                postSuccessListener?.onEnd()
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
                postSuccessListener?.onFailure(e?.message!!)
                postSuccessListener?.onEnd()
            } catch (e: NoInternetException) {
                postSuccessListener?.onEnd()
                postSuccessListener?.onFailure(e?.message!!)
            }
        }

    }
    fun deletePost(header:String,id:Int) {
        postSuccessListener?.onStarted()
        Coroutines.main {
            try {
                idPost= IDPost(id)
                Log.e("createToken", "createToken" + Gson().toJson(idPost))
                val response = repository.deletePost(header,idPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                if (response.success!!){
                    postSuccessListener?.onSuccess(response?.message!!)
                }
                else{
                    postSuccessListener?.onFailure(response?.message!!)
                }
                postSuccessListener?.onEnd()
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
                postSuccessListener?.onFailure(e?.message!!)
                postSuccessListener?.onEnd()
            } catch (e: NoInternetException) {
                postSuccessListener?.onEnd()
                postSuccessListener?.onFailure(e?.message!!)
            }
        }

    }
}