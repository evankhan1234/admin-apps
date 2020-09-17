package com.evan.admin.ui.auth

import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.evan.admin.data.network.post.AuthPost
import com.evan.admin.data.repositories.UserRepository
import com.evan.admin.ui.auth.interfaces.ICustomerOrderCountListener
import com.evan.admin.ui.auth.interfaces.ILastFiveSalesListener
import com.evan.admin.ui.auth.interfaces.IStoreCountListener
import com.evan.admin.ui.home.Listener
import com.evan.admin.util.ApiException
import com.evan.admin.util.Coroutines
import com.evan.admin.util.NoInternetException
import com.evan.admin.util.SharedPreferenceUtil
import com.google.gson.Gson
import okhttp3.MultipartBody
import okhttp3.RequestBody


class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    var authPost: AuthPost? = null
    var name: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null
    var customerOrderCountListener: ICustomerOrderCountListener?=null
    var lastFiveSalesListener: ILastFiveSalesListener?=null
    var authListener: AuthListener? = null
    var AddListener: Listener? = null
    var storeCountListener: IStoreCountListener?=null
    fun onLoginButtonClick(view: View) {
        authListener?.onStarted()
        if ( email.isNullOrEmpty()) {
            authListener?.onFailure("Email is Empty")
            return
        }
        else if ( password.isNullOrEmpty()) {
            authListener?.onFailure("Password is Empty")
            return
        }

        Coroutines.main {
            try {
                authPost = AuthPost(email!!, password!!)
                val authResponse = repository.userLoginFor(authPost!!)
                Log.e("response", "response" + authResponse.message)
                if(authResponse.success!!)
                {

                    SharedPreferenceUtil.saveShared(
                        view.context,
                        SharedPreferenceUtil.TYPE_AUTH_TOKEN,
                        authResponse.jwt!!
                    )
                    authListener?.onSuccess(authResponse.data!!)

                }
                else{
                    authListener?.onFailure(authResponse.message!!)
                }

            } catch (e: ApiException) {
                authListener?.onFailure(e.message!!)
                authListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                authListener?.onFailure(e?.message!!)

            }
        }

    }


    fun uploadImage(part: MultipartBody.Part, body: RequestBody) {
        //else success
        Coroutines.main {
            try {
                val uploadImageResponse = repository.createImage(part, body)
                if (uploadImageResponse.success!!) {
                    Log.e("imageUpload", "" + Gson().toJson(uploadImageResponse));
                    AddListener?.Success(uploadImageResponse.img_address!!)
                } else {
                    // val alerts = repository.getDeliveryistAPI(1)
                    /**Save in local db*/
                    //   repository.saveAllAlert(alerts)
                    //listOfDelivery.value = alerts
                    AddListener?.Failure(uploadImageResponse.message!!)
                    Log.e("imageUpload", "" + Gson().toJson(uploadImageResponse));

                }
            } catch (e: ApiException) {
                AddListener?.Success(e.message!!)
//                deliveryAddListener!!.onFailure(e.message!!)
                Log.e("imageUpload", "" + (e.message!!));
            } catch (e: NoInternetException) {
                AddListener?.Success(e.message!!)
//                deliveryAddListener!!.onFailure(e.message!!)
                Log.e("imageUpload", "" + (e.message!!));
            }

        }

    }

    fun getLasFive(header:String) {

        Coroutines.main {
            try {

                val response = repository.getLasFive(header)
                Log.e("Search", "Search" + Gson().toJson(response))
                lastFiveSalesListener?.onLast(response.data!!)

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getCustomerOrderCount(header:String) {

        Coroutines.main {
            try {

                val response = repository.getCustomerOrderCount(header)
                Log.e("Search", "Search" + Gson().toJson(response))
                customerOrderCountListener?.onCount(response.data!!)

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun getStoreCount(header:String) {

        Coroutines.main {
            try {
                val response = repository.getStoreCount(header)
                Log.e("Search", "Search" + Gson().toJson(response))
                storeCountListener?.onStore(response.data!!)

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

}