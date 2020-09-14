package com.evan.admin.data.repositories

import com.evan.admin.data.db.AppDatabase
import com.evan.admin.data.network.MyApi
import com.evan.admin.data.network.SafeApiRequest
import com.evan.admin.data.network.post.*
import com.evan.admin.data.network.responses.*

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

    suspend fun getProductList(header:String,post: ProductPost): ProductListResponses {
        return apiRequest { api.getProductList(header,post) }
    }
    suspend fun getProductSearchList(header:String,post: ProductSearchPost): ProductListResponses {
        return apiRequest { api.getProductSearchList(header,post) }
    }
    suspend fun getShopType(): ShopTypeResponses {
        return apiRequest { api.getShopType() }
    }
    suspend fun getUnit(): UnitResponses {
        return apiRequest { api.getUnit() }
    }
    suspend fun postProduct(header:String,productPost:  SystemProductPost): BasicResponses {
        return apiRequest { api.createProduct(header,productPost!!) }
    }
    suspend fun updateProduct(header:String,productPost:  SystemUpdatedProductPost): BasicResponses {
        return apiRequest { api.updateProduct(header,productPost!!) }
    }
    suspend fun getCustomerList(header:String,post: LimitPost): CustomerListResponses {
        return apiRequest { api.getCustomerList(header,post)}
    }

    suspend fun updateCustomer(header:String,post: CustomerUpdatePost): BasicResponses {
        return apiRequest { api.updateCustomer(header,post)}
    }
}