/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import edu.tacoma.uw.huskyhaze.network.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Activity class for the weather.
 */
class WeatherActivity : AppCompatActivity() {

    /**
     * Initializes the weather page and fetches the weather forecast.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        fetchWeatherForecast()
    }

    /**
     * Fetches the weather forecast from the OpenWeather API.
     */
    private fun fetchWeatherForecast() {
        val weatherService = WeatherService.create()
        val apiKey = getString(R.string.open_weather_api_key)
        // UWT coordinates
        val latitude = 47.24
        val longitude = -122.43

        CoroutineScope(Dispatchers.IO).launch {
            val response =
                weatherService.getWeatherForecastByCoordinates(latitude, longitude, apiKey)
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    Log.d("WeatherForecastResponse", weatherData.toString())
                    if (weatherData != null) {
                        val forecastAdapter = ForecastAdapter(weatherData.daily)
                        runOnUiThread {
                            val recyclerView = findViewById<androidx.recyclerview.widget.RecyclerView>(R.id.forecastRecyclerView)
                            recyclerView.adapter = forecastAdapter
                            recyclerView.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(this@WeatherActivity)
                        }
                    }

                } else {
                    Log.e("WeatherForecastResponse", response.errorBody().toString())
                }
            }
        }
    }
}