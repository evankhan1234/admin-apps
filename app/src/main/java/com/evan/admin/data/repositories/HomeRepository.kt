package com.evan.admin.data.repositories

import com.evan.admin.data.db.AppDatabase
import com.evan.admin.data.network.MyApi
import com.evan.admin.data.network.PushApi
import com.evan.admin.data.network.SafeApiRequest
import com.evan.admin.data.network.post.*
import com.evan.admin.data.network.responses.*

class HomeRepository (
    private val api: MyApi,
    private val push_api: PushApi
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
    suspend fun getPublicPostPagination(header:String,post: PublicForPost): PostResponses {
        return apiRequest { api.getPublicPostPagination(header,post) }
    }
    suspend fun getOwnPostPagination(header:String,post: OwnForPost): PostResponses {
        return apiRequest { api.getOwnPostPagination(header,post) }
    }
    suspend fun updatedLikeCount(header:String,post: LikeCountPost): BasicResponses {
        return apiRequest { api.updatedLikeCount(header,post) }
    }
    suspend fun createdLove(header:String,post: LovePost): BasicResponses {
        return apiRequest { api.createdLove(header,post) }
    }
    suspend fun deletedLove(header:String,post: LovePost): BasicResponses {
        return apiRequest { api.deletedLove(header,post) }
    }
    suspend fun createdNewsFeedPost(header:String,post: NewsfeedPost): BasicResponses {
        return apiRequest { api.createdNewsFeedPost(header,post) }
    }
    suspend fun getComments(header:String,post: CommentsPost): CommentsResponse {
        return apiRequest { api.getComments(header,post) }
    }
    suspend fun createComments(header:String,post: CommentsForPost): BasicResponses {
        return apiRequest { api.createComments(header,post) }
    }
    suspend fun updateOwnPost(header:String,post: OwnUpdatedPost): BasicResponses {
        return apiRequest { api.updateOwnPost(header,post) }
    }
    suspend fun createdLike(header:String,post: CommentsPost): BasicResponses {
        return apiRequest { api.createdLike(header,post) }
    }
    suspend fun deletedLike(header:String,post: CommentsPost): BasicResponses {
        return apiRequest { api.deletedLike(header,post) }
    }
    suspend fun updatedCommentsLikeCount(header:String,post: LikeCountPost): BasicResponses {
        return apiRequest { api.updatedCommentsLikeCount(header,post) }
    }
    suspend fun createReply(header:String,post: ReplyForPost): BasicResponses {
        return apiRequest { api.createReply(header,post) }
    }
    suspend fun getReply(header:String,post: ReplyPost): ReplyResponses {
        return apiRequest { api.getReply(header,post) }
    }
    suspend fun getNotice(header:String,post: NoticePost): NoticeResponses {
        return apiRequest { api.getNotice(header,post) }
    }
    suspend fun createNotice(header:String,post: NoticeCreatePost): BasicResponses {
        return apiRequest { api.createNotice(header,post) }
    }
    suspend fun updateNotice(header:String,post: NoticeUpdatePost): BasicResponses {
        return apiRequest { api.updateNotice(header,post) }
    }
    suspend fun getCustomerDetailsList(header:String,post: OrderIdPost): OrderDetailsResponse {
        return apiRequest { api.getCustomerDetailsList(header,post) }
    }
    suspend fun getCustomerOrderInformation(header:String,post: OrderIdPost): CustomerOrderResponses {
        return apiRequest { api.getCustomerOrderInformation(header,post) }
    }
    suspend fun getDeliveredPagination(header:String,post: LimitPost): OrderResponses {
        return apiRequest { api.getDeliveredPagination(header,post) }
    }
    suspend fun getPendingPagination(header:String,post: LimitPost): OrderResponses {
        return apiRequest { api.getPendingPagination(header,post) }
    }
    suspend fun getProcessingPagination(header:String,post: LimitPost): OrderResponses {
        return apiRequest { api.getProcessingPagination(header,post) }
    }
    suspend fun getFirebaseToken(): TokenResponses {
        return apiRequest { api.getToken() }
    }
    suspend fun sendPush(header:String, post: PushPost): PushResponses {
        return apiRequest { push_api.sendPush(header,post) }
    }
}