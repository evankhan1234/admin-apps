package com.evan.admin.data.repositories

import com.evan.admin.data.db.AppDatabase
import com.evan.admin.data.network.MyApi
import com.evan.admin.data.network.SafeApiRequest
import com.evan.admin.data.network.post.AuthPost
import com.evan.admin.data.network.post.IDPost
import com.evan.admin.data.network.post.LimitPost
import com.evan.admin.data.network.post.LoginResponse
import com.evan.admin.data.network.responses.AuthResponse
import com.evan.admin.data.network.responses.BasicResponses
import com.evan.admin.data.network.responses.PostResponses
import com.evan.admin.data.network.responses.ShopResponses

class HomeRepository (
    private val api: MyApi
) : SafeApiRequest() {


    suspend fun getInActivePost(header:String,post: LimitPost): PostResponses {
        return apiRequest { api.getInActivePost(header,post)}
    }

    suspend fun updatePost(header:String,post: IDPost): BasicResponses {
        return apiRequest { api.updatePost(header,post)}
    }
    suspend fun deletePost(header:String,post: IDPost): BasicResponses {
        return apiRequest { api.deletePost(header,post)}
    }
    suspend fun getInactiveShopList(header:String): ShopResponses {
        return apiRequest { api.getInactiveShopList(header)}
    }
    suspend fun updateShop(header:String,post: IDPost): BasicResponses {
        return apiRequest { api.updateShop(header,post)}
    }
    suspend fun getActiveShopList(header:String): ShopResponses {
        return apiRequest { api.getActiveShopList(header)}
    }
    suspend fun updateActiveShop(header:String,post: IDPost): BasicResponses {
        return apiRequest { api.updateActiveShop(header,post)}
    }
}