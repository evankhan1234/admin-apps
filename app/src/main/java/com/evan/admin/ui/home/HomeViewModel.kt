package com.evan.admin.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.admin.data.network.post.IDPost
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.store.inactive_shop.IShopListListener
import com.evan.admin.ui.home.store.inactive_shop.IShopSuccessListener
import com.evan.admin.util.ApiException
import com.evan.admin.util.Coroutines
import com.evan.admin.util.NoInternetException
import com.google.gson.Gson

class HomeViewModel (
    private val repository: HomeRepository
) : ViewModel() {
    var idPost:IDPost?= null
    var shopListListener: IShopListListener?=null
    var shopSuccessListener: IShopSuccessListener?=null
    fun getShops(token:String) {
        shopListListener?.onStarted()
        Coroutines.main {
            try {
                val authResponse = repository.getInactiveShopList(token)
                Log.e("response", "response" + Gson().toJson(authResponse))
                if (authResponse.success!!){
                    shopListListener?.shop(authResponse?.data!!)
                }
                else{
                    shopListListener?.onFailure(authResponse.message!!)
                }


                shopListListener?.onEnd()
            } catch (e: ApiException) {
                shopListListener?.onFailure(e.message!!)
                shopListListener?.onEnd()
            } catch (e: NoInternetException) {
                shopListListener?.onEnd()
                shopListListener?.onFailure(e.message!!)
            }
        }

    }

    fun updateShop(header:String,id:Int) {
        shopSuccessListener?.onStarted()
        Coroutines.main {
            try {
                idPost= IDPost(id)
                Log.e("createToken", "createToken" + Gson().toJson(idPost))
                val response = repository.updateShop(header,idPost!!)
                Log.e("createToken", "createToken" + Gson().toJson(response))
                if (response.success!!){
                    shopSuccessListener?.onSuccess(response?.message!!)
                }
                else{
                    shopSuccessListener?.onFailure(response?.message!!)
                }
                shopSuccessListener?.onEnd()
            } catch (e: ApiException) {
                Log.e("createToken", "createToken" +e?.message)
                shopSuccessListener?.onFailure(e?.message!!)
                shopSuccessListener?.onEnd()
            } catch (e: NoInternetException) {
                shopSuccessListener?.onEnd()
                shopSuccessListener?.onFailure(e?.message!!)
            }
        }

    }
}