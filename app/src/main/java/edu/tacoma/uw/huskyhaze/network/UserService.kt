/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze.network

import edu.tacoma.uw.huskyhaze.models.LoginRequest
import edu.tacoma.uw.huskyhaze.models.RegisterRequest
import edu.tacoma.uw.huskyhaze.models.RegisterResponse
import edu.tacoma.uw.huskyhaze.models.UserInfo
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.POST

interface UserService {
    @POST("login.php")
    suspend fun login(@Body request: LoginRequest): Response<UserInfo>

    @POST("register_user.php")
    suspend fun register(@Body request: RegisterRequest): Response<RegisterResponse>


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