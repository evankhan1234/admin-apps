package com.evan.admin.ui.home

import android.util.Log
import androidx.lifecycle.ViewModel
import com.evan.admin.data.network.post.*
import com.evan.admin.data.repositories.HomeRepository
import com.evan.admin.ui.home.newsfeed.ownpost.IPostListener
import com.evan.admin.ui.home.newsfeed.publicpost.comments.ICommentsListener
import com.evan.admin.ui.home.newsfeed.publicpost.comments.ICommentsPostListener
import com.evan.admin.ui.home.newsfeed.publicpost.comments.ISucceslistener
import com.evan.admin.ui.home.newsfeed.publicpost.reply.IReplyListener
import com.evan.admin.ui.home.newsfeed.publicpost.reply.IReplyPostListener
import com.evan.admin.ui.home.notice.INoticeCreateListener
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
    var replyPost:ReplyPost?=null
    var postListener: IPostListener?=null
    var commentsPostListener: ICommentsPostListener?=null
    var noticeCreateListener: INoticeCreateListener?=null
    var commentsListener: ICommentsListener?=null
    var succeslistener: ISucceslistener?=null
    var replyListener: IReplyListener?=null
    var replyPostListener: IReplyPostListener?=null
    var newsfeedPost:NewsfeedPost?=null
    var ownUpdatedPost:OwnUpdatedPost?=null
    var likeCountPost:LikeCountPost?=null
    var commentsForPost:CommentsForPost?=null
    var replyForPost:ReplyForPost?=null
    var commentsPost:CommentsPost?=null
    var noticeCreatePost:NoticeCreatePost?=null
    var noticeUpdatePost:NoticeUpdatePost?=null
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
    fun createdNewsFeedPost(header:String,Name:String,content:String,picture:String,created:String,status:Int,type:Int,image:String,love:Int) {
        postListener?.onStarted()
        Coroutines.main {
            try {
                newsfeedPost= NewsfeedPost(Name!!,content!!,picture!!,created!!,status!!,type!!,image!!,love!!)
                Log.e("Search", "Search" + Gson().toJson(newsfeedPost))
                val response = repository.createdNewsFeedPost(header,newsfeedPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                postListener?.onSuccess(response?.message!!)
                postListener?.onEnd()

            } catch (e: ApiException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            }
        }

    }
    fun getComments(header:String,postId:Int,type:Int) {
        commentsListener?.onStarted()
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                Log.e("Search", "Search" + Gson().toJson(commentsPost))
                val response = repository.getComments(header,commentsPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                commentsListener?.load(response?.data!!)
                commentsListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                commentsListener?.onEnd()
            } catch (e: NoInternetException) {
                commentsListener?.onEnd()
            }
        }

    }
    fun getCommentsAgain(header:String,postId:Int,type:Int) {

        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                Log.e("Search", "Search" + Gson().toJson(commentsPost))
                val response = repository.getComments(header,commentsPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))

                succeslistener?.onShow()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun updateNewsFeedPost(header:String,id:Int,Name:String,content:String,picture:String,type:Int,image:String) {
        postListener?.onStarted()
        Coroutines.main {
            try {
                ownUpdatedPost= OwnUpdatedPost(id,Name!!,content!!,picture!!,type!!,image!!)
                Log.e("Search", "Search" + Gson().toJson(ownUpdatedPost))
                val response = repository.updateOwnPost(header,ownUpdatedPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                postListener?.onSuccess(response?.message!!)
                postListener?.onEnd()

            } catch (e: ApiException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                postListener?.onEnd()
                postListener?.onFailure(e?.message!!)
            }
        }

    }
    fun updatedCommentsLikeCount(header:String,id: Int,love:Int) {
        Coroutines.main {
            try {
                likeCountPost= LikeCountPost(id,love)
                val response = repository.updatedCommentsLikeCount(header,likeCountPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createdLike(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                val response = repository.createdLike(header,commentsPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun deletedLike(header:String,postId: Int,type:Int) {
        Coroutines.main {
            try {
                commentsPost= CommentsPost(postId,type)
                val response = repository.deletedLike(header,commentsPost!!)
                Log.e("response", "response" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }
    fun createComments(header:String,Name:String,content:String,created:String,status:Int,type:Int,image:String,love:Int,postId:Int) {
        commentsPostListener?.onStarted()
        Coroutines.main {
            try {
                commentsForPost= CommentsForPost(Name!!,content!!,created!!,status!!,type!!,image!!,love!!,postId!!)
                Log.e("Search", "Search" + Gson().toJson(newsfeedPost))
                val response = repository.createComments(header,commentsForPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                commentsPostListener?.onSuccess(response?.message!!)
                commentsPostListener?.onEnd()

            } catch (e: ApiException) {
                commentsPostListener?.onEnd()
                commentsPostListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                commentsPostListener?.onEnd()
                commentsPostListener?.onFailure(e?.message!!)
            }
        }

    }
    fun getReply(header:String,commentId:Int) {
        replyListener?.onStarted()
        Coroutines.main {
            try {
                replyPost= ReplyPost(commentId)
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getReply(header,replyPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))
                replyListener?.load(response?.data!!)
                replyListener?.onEnd()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {
                replyListener?.onEnd()
            } catch (e: NoInternetException) {
                replyListener?.onEnd()
            }
        }

    }
    fun createReply(header:String,Name:String,content:String,created:String,status:Int,type:Int,image:String,commentsId:Int) {
        replyPostListener?.onStarted()
        Coroutines.main {
            try {
                replyForPost= ReplyForPost(Name!!,content!!,created!!,status!!,type!!,image!!,commentsId!!)
                Log.e("Search", "Search" + Gson().toJson(replyForPost))
                val response = repository.createReply(header,replyForPost!!)
                Log.e("response", "response" + Gson().toJson(response))
                replyPostListener?.onSuccess(response?.message!!)
                replyPostListener?.onEnd()

            } catch (e: ApiException) {
                replyPostListener?.onEnd()
                replyPostListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                replyPostListener?.onEnd()
                replyPostListener?.onFailure(e?.message!!)
            }
        }

    }
    fun geReplyAgain(header:String,commentId:Int) {

        Coroutines.main {
            try {
                replyPost= ReplyPost(commentId)
                Log.e("Search", "Search" + Gson().toJson(replyPost))
                val response = repository.getReply(header,replyPost!!)
                Log.e("Search", "Search" + Gson().toJson(response))

                succeslistener?.onShow()
                Log.e("Search", "Search" + Gson().toJson(response))

            } catch (e: ApiException) {

            } catch (e: NoInternetException) {

            }
        }

    }

    fun createNewPost(header:String,title:String,content:String,picture:String,created:String,status:Int,type:Int) {
        commentsPostListener?.onStarted()
        Coroutines.main {
            try {
                noticeCreatePost= NoticeCreatePost(title!!,content!!,picture,created!!,status!!,type!!)
                Log.e("Search", "Search" + Gson().toJson(noticeCreatePost))
                val response = repository.createNotice(header,noticeCreatePost!!)
                Log.e("response", "response" + Gson().toJson(response))
                noticeCreateListener?.onSuccess(response?.message!!)
                noticeCreateListener?.onEnd()

            } catch (e: ApiException) {
                noticeCreateListener?.onEnd()
                noticeCreateListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                noticeCreateListener?.onEnd()
                noticeCreateListener?.onFailure(e?.message!!)
            }
        }

    }
    fun createUpdatePost(header:String,id:Int,title:String,content:String,picture:String,created:String) {
        commentsPostListener?.onStarted()
        Coroutines.main {
            try {
                noticeUpdatePost= NoticeUpdatePost(id!!,title!!,content!!,picture,created!!)
                Log.e("Search", "Search" + Gson().toJson(noticeUpdatePost))
                val response = repository.updateNotice(header,noticeUpdatePost!!)
                Log.e("response", "response" + Gson().toJson(response))
                noticeCreateListener?.onSuccess(response?.message!!)
                noticeCreateListener?.onEnd()

            } catch (e: ApiException) {
                noticeCreateListener?.onEnd()
                noticeCreateListener?.onFailure(e?.message!!)
            } catch (e: NoInternetException) {
                noticeCreateListener?.onEnd()
                noticeCreateListener?.onFailure(e?.message!!)
            }
        }

    }
}