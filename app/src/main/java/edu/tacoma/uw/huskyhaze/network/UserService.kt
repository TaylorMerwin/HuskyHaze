package edu.tacoma.uw.huskyhaze.network

import edu.tacoma.uw.huskyhaze.models.LoginRequest
import edu.tacoma.uw.huskyhaze.models.LoginResponse
import edu.tacoma.uw.huskyhaze.models.UserInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface UserService {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET("user_info.php")
    suspend fun getUserInfo(@Query("user_id") userId: Int): Response<UserInfo>


    companion object {
        private const val BASE_URL = "https://students.washington.edu/tmerwin/"

        fun create(): UserService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(UserService::class.java)
        }
    }
}