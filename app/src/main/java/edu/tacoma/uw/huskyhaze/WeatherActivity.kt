package edu.tacoma.uw.huskyhaze

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import edu.tacoma.uw.huskyhaze.network.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WeatherActivity : AppCompatActivity() {
    private lateinit var temperatureTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_weather)
        temperatureTextView = findViewById(R.id.temperatureTextView)
        fetchCurrentWeather()
    }

    @SuppressLint("SetTextI18n")
    private fun fetchCurrentWeather() {
        val weatherService = WeatherService.create()
        val apiKey = getString(R.string.open_weather_api_key)
        // UWT coordinates
        val latitude = 47.24
        val longitude = -122.43

        CoroutineScope(Dispatchers.IO).launch {
            val response =
                weatherService.getCurrentWeatherByCoordinates(latitude, longitude, apiKey)

            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    val weatherData = response.body()
                    var currentTemp = 0.0
                    if (weatherData != null) {
                        currentTemp = weatherData.main.temp;
                    }
                    temperatureTextView.text = "Temperature: $currentTemp"
                    Log.d("WeatherResponse", weatherData.toString())
                } else {
                    Log.e("WeatherResponse", response.errorBody().toString())
                    temperatureTextView.text = "Failed to get weather data"
                }

            }


        }
    }
}