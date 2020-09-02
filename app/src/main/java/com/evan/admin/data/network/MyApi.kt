package com.evan.admin.data.network


import com.evan.admin.data.network.post.*
import com.evan.admin.data.network.responses.*
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface MyApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @POST("admin-login.php")
    suspend fun userLoginFor(
        @Body authPost: AuthPost
    ) : Response<LoginResponse>

    @POST("get-in-active-post.php")
    suspend fun  getInActivePost(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<PostResponses>

    @POST("update-in-active-post.php")
    suspend fun  updatePost(
        @Header("Authorization") Authorization:String,
        @Body idPost: IDPost
    ): Response<BasicResponses>
    @POST("update-in-active-status.php")
    suspend fun  updateShop(
        @Header("Authorization") Authorization:String,
        @Body idPost: IDPost
    ): Response<BasicResponses>
    @POST("system-product-pagination.php")
    suspend fun getProductList(
        @Header("Authorization") Authorization:String,
        @Body systemPost: ProductPost
    ): Response<ProductListResponses>
    @POST("searching-system-list.php")
    suspend fun getProductSearchList(
        @Header("Authorization") Authorization:String,
        @Body systemPost: ProductSearchPost
    ): Response<ProductListResponses>
    @GET("shop-type.php")
    suspend fun getShopType(
    ): Response<ShopTypeResponses>
    @POST("update-active-status.php")
    suspend fun  updateActiveShop(
        @Header("Authorization") Authorization:String,
        @Body idPost: IDPost
    ): Response<BasicResponses>
    @POST("delete-post.php")
    suspend fun  deletePost(
        @Header("Authorization") Authorization:String,
        @Body idPost: IDPost
    ): Response<BasicResponses>
    @GET("get-inactive-shop.php")
    suspend fun getInactiveShopList(
        @Header("Authorization") Authorization:String
    ): Response<ShopResponses>

    @GET("get-active-shop.php")
    suspend fun getActiveShopList(
        @Header("Authorization") Authorization:String
    ): Response<ShopResponses>
    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ) : Response<AuthResponse>
    @GET("quotes")
    suspend fun getQuotes() : Response<QuotesResponse>

    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : MyApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://192.168.0.108/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

