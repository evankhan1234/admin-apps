package com.evan.admin.data.repositories

import com.evan.admin.data.db.AppDatabase

import com.evan.admin.data.network.MyApi
import com.evan.admin.data.network.SafeApiRequest
import com.evan.admin.data.network.post.AuthPost
import com.evan.admin.data.network.post.LoginResponse
import com.evan.admin.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.RequestBody


class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }
    suspend fun userLoginFor(auth: AuthPost): LoginResponse {
        return apiRequest { api.userLoginFor(auth) }
    }

    suspend fun createImage(part: MultipartBody.Part, body: RequestBody): ImageResponse {
        return apiRequest { api.createProfileImage(part,body) }
    }
    suspend fun getLasFive(header:String): LastFiveSalesCountResponses {
        return apiRequest { api.getLasFive(header) }
    }
    suspend fun getCustomerOrderCount(header:String): CustomerOrderCountResponses {
        return apiRequest { api.getCustomerOrderCount(header) }
    }
    suspend fun getStoreCount(header:String): StoreCountResponses {
        return apiRequest { api.getStoreCount(header) }
}}