package com.evan.admin.data.network


import com.evan.admin.data.network.post.*
import com.evan.admin.data.network.responses.*
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

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
    @Multipart
    @POST("create-sign-up-image.php")
    suspend fun createProfileImage(
        @Part file: MultipartBody.Part?, @Part("uploaded_file") requestBody: RequestBody?
    ): Response<ImageResponse>
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
    @POST("update-customer-status.php")
    suspend fun  updateCustomer(
        @Header("Authorization") Authorization:String,
        @Body idPost: CustomerUpdatePost
    ): Response<BasicResponses>

    @POST("update-in-active-status.php")
    suspend fun  updateShop(
        @Header("Authorization") Authorization:String,
        @Body idPost: IDPost
    ): Response<BasicResponses>
    @POST("system-admin-product-pagination.php")
    suspend fun getProductList(
        @Header("Authorization") Authorization:String,
        @Body systemPost: ProductPost
    ): Response<ProductListResponses>

    @POST("get-customer-list.php")
    suspend fun getCustomerList(
        @Header("Authorization") Authorization:String,
        @Body limitPost: LimitPost
    ): Response<CustomerListResponses>

    @POST("searching-system-list.php")
    suspend fun getProductSearchList(
        @Header("Authorization") Authorization:String,
        @Body systemPost: ProductSearchPost
    ): Response<ProductListResponses>
    @GET("shop-type.php")
    suspend fun getShopType(
    ): Response<ShopTypeResponses>
    @GET("unit-details.php")
    suspend fun getUnit(
    ): Response<UnitResponses>
    @POST("create-system-product.php")
    suspend fun createProduct(
        @Header("Authorization") Authorization:String,
        @Body productPost: SystemProductPost
    ): Response<BasicResponses>
    @POST("update-system-product.php")
    suspend fun updateProduct(
        @Header("Authorization") Authorization:String,
        @Body productPost: SystemUpdatedProductPost
    ): Response<BasicResponses>
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
                .connectTimeout(1, TimeUnit.MINUTES)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(15, TimeUnit.SECONDS)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
                .baseUrl("http://192.168.0.110/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

