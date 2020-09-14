package com.evan.admin.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.admin.data.network.post.IDPost
import com.evan.admin.data.network.post.ProductPost
import com.evan.admin.data.network.post.SystemProductPost
import com.evan.admin.data.network.post.SystemUpdatedProductPost
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.store.inactive_shop.IShopListListener
import com.evan.admin.ui.home.store.inactive_shop.IShopSuccessListener
import com.evan.admin.ui.home.store.products.ICreateProductListener
import com.evan.admin.ui.home.store.products.IUnitListener
import com.evan.admin.ui.home.store.products.ShopTypeInterface
import com.evan.admin.util.ApiException
import com.evan.admin.util.Coroutines
import com.evan.admin.util.NoInternetException
import com.google.gson.Gson

class HomeViewModel (
    private val repository: HomeRepository
) : ViewModel() {
    var idPost:IDPost?= null
    var systemProductPost:SystemProductPost?= null
    var systemUpdatedProductPost:SystemUpdatedProductPost?= null
    var shopListListener: IShopListListener?=null
    var shopSuccessListener: IShopSuccessListener?=null
    var unitListener: IUnitListener?=null
    var shopTypeListener: ShopTypeInterface? = null
    var createProductListener: ICreateProductListener?=null
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
    fun getActiveShops(token:String) {
        shopListListener?.onStarted()
        Coroutines.main {
            try {
                val authResponse = repository.getActiveShopList(token)
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
    fun updateActiveShop(header:String,id:Int) {
        shopSuccessListener?.onStarted()
        Coroutines.main {
            try {
                idPost= IDPost(id)
                Log.e("createToken", "createToken" + Gson().toJson(idPost))
                val response = repository.updateActiveShop(header,idPost!!)
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
    fun getUnit() {
        Coroutines.main {
            try {
                val authResponse = repository.getUnit()
                unitListener?.unit(authResponse?.data!!)
                Log.e("response", "response" + Gson().toJson(authResponse))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

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
    fun postProduct(header:String,name:String,details:String,productCode:String,productImage:String,unitId:Int,sellPrice:Double,supplierPrice:Double,shopId:Int,stock:Int,quantity:Int,discount:Double,created:String,status:Int) {
        createProductListener?.started()
        Coroutines.main {
            try {
                systemProductPost = SystemProductPost(name,details,productCode,productImage,unitId,sellPrice,supplierPrice,shopId, stock,quantity,discount, created,status)
                Log.e("response", "response" + Gson().toJson(systemProductPost))
                val response = repository.postProduct(header,systemProductPost!!)
                createProductListener?.show(response?.message!!)
                createProductListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createProductListener?.end()
                createProductListener?.failure(e?.message!!)
            } catch (e: NoInternetException) {
                createProductListener?.end()
            }
        }

    }
    fun postUpdateProduct(header:String,id:Int,name:String,details:String,productCode:String,productImage:String,unitId:Int,sellPrice:Double,supplierPrice:Double,shopId:Int,stock:Int,quantity:Int,discount:Double,created:String,status:Int) {
        createProductListener?.started()
        Coroutines.main {
            try {
                systemUpdatedProductPost = SystemUpdatedProductPost(id,name,details,productCode,productImage,unitId,sellPrice,supplierPrice,shopId, stock,quantity,discount, created,status)
                Log.e("response", "response" + Gson().toJson(systemUpdatedProductPost))
                val response = repository.updateProduct(header,systemUpdatedProductPost!!)
                createProductListener?.show(response?.message!!)
                createProductListener?.end()
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {
                createProductListener?.end()
                createProductListener?.failure(e?.message!!)
            } catch (e: NoInternetException) {
                createProductListener?.end()
            }
        }

    }
}