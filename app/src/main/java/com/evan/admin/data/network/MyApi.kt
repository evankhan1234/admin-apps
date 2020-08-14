package com.evan.admin.data.network


import com.evan.admin.data.network.post.AuthPost
import com.evan.admin.data.network.post.IDPost
import com.evan.admin.data.network.post.LimitPost
import com.evan.admin.data.network.post.LoginResponse
import com.evan.admin.data.network.responses.AuthResponse
import com.evan.admin.data.network.responses.BasicResponses
import com.evan.admin.data.network.responses.PostResponses
import com.evan.admin.data.network.responses.QuotesResponse
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

    @POST("delete-post.php")
    suspend fun  deletePost(
        @Header("Authorization") Authorization:String,
        @Body idPost: IDPost
    ): Response<BasicResponses>

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
                .baseUrl("http://192.168.0.105/stationary/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MyApi::class.java)
        }
    }

}

