/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze.network

import edu.tacoma.uw.huskyhaze.models.WeatherData
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {


    @GET("onecall")
    suspend fun getCurrentWeatherByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("exclude", encoded = true) exclusions: String = "daily,minutely,hourly,alerts",
        @Query("units") units: String = "imperial"
    ): Response<WeatherData.WeatherData>


    @GET("onecall")
    suspend fun getWeatherForecastByCoordinates(
        @Query("lat") latitude: Double,
        @Query("lon") longitude: Double,
        @Query("appid") apiKey: String,
        @Query("exclude", encoded = true) exclusions: String = "current,minutely,hourly,alerts",
        @Query("units") units: String = "imperial"
    ): Response<WeatherData.WeatherData>

    companion object {
        private const val BASE_URL = "https://api.openweathermap.org/data/3.0/"

        fun create(): WeatherService {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(WeatherService::class.java)
        }
    }

}