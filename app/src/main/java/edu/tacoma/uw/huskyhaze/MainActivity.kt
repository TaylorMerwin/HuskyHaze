/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import edu.tacoma.uw.huskyhaze.network.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    private val REQUEST_MAPS_ACTIVITY = 1
    private var latitude = 47.24 // Default UWT latitude
    private var longitude = -122.43 // Default UWT longitude
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        Handler(Looper.getMainLooper()).postDelayed({
            val sharedPreferences = getSharedPreferences("user_prefs", Context.MODE_PRIVATE)
            val userId = sharedPreferences.getInt("user_id", -1)
            val userName = sharedPreferences.getString("user_name", "Guest")
            Log.i("MainActivity", "User ID: $userId, User Name: $userName")
        }, 2000)



        val weatherButton = findViewById<Button>(R.id.weatherButton)
        val newsButton = findViewById<Button>(R.id.newsButton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val mapsButton = findViewById<Button>(R.id.mapsButton)

        weatherButton.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

        newsButton.setOnClickListener {
            val intent = Intent(this, NewsActivity::class.java)
            startActivity(intent)
        }
        mapsButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java)
            intent.putExtra("latitude", latitude)
            intent.putExtra("longitude", longitude)
            startActivityForResult(intent, REQUEST_MAPS_ACTIVITY)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
        fetchCurrentWeather()
    }

    /**
     * This method is called when the MapsActivity returns a result.
     * @param requestCode The request code
     * @param resultCode The result code
     * @param data The intent data
     */
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_MAPS_ACTIVITY && resultCode == RESULT_OK) {
            // Receive updated latitude and longitude from MapsActivity
            latitude = data?.getDoubleExtra("latitude", latitude) ?: latitude
            longitude = data?.getDoubleExtra("longitude", longitude) ?: longitude

            fetchCurrentWeather()
        }
    }

    /**
     * This method fetches the current weather data from the OpenWeather API.
     */
    private fun fetchCurrentWeather() {
        val weatherService = WeatherService.create()
        val apiKey = getString(R.string.open_weather_api_key)
        // UWT coordinates
//        val latitude = 47.24
//        val longitude = -122.43
        var currentTemp = 0
        var currentWeather = "unknown"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    weatherService.getCurrentWeatherByCoordinates(latitude, longitude, apiKey)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        if (weatherData != null) {
                            currentTemp = weatherData.current.temp.roundToInt()
                            currentWeather = weatherData.current.weather[0].main.lowercase()
                            val iconCode = weatherData.current.weather[0].icon
                            val iconUrl = "https://openweathermap.org/img/wn/${iconCode}@2x.png"
                            displayWeather(currentTemp, currentWeather, iconUrl)
                        } else {
                            Log.e("WeatherFetchError", response.errorBody().toString())
                        }
                    }
                    else {
                        Log.e("WeatherFetchError", response.errorBody().toString())
                    }
                }
            }
            catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Log.e("WeatherFetchError", "Unable to fetch weather data: ${e.message}")
                }
            }
        }

    }

    /**
     * This method displays the weather information on the main activity.
     * @param temperature The current temperature
     * @param weatherType The current weather type
     * @param iconUrl The URL of the weather icon
     */
    private fun displayWeather(temperature: Int?, weatherType: String?, iconUrl: String?) {
        val greetingInfo = createWelcomeGreeting(temperature, weatherType)
        val greetingTextView = findViewById<TextView>(R.id.GreetingTextView)
        val greetingInfoTextView = findViewById<TextView>(R.id.GreetingInfoTextView)
        val weatherIconImageView = findViewById<ImageView>(R.id.weatherIconImageView)
        greetingTextView.text = getTimeOfDayGreeting()
        greetingInfoTextView.text = greetingInfo
        Log.d("WeatherGreeting", getTimeOfDayGreeting())
        Log.d("WeatherGreetingInfo", greetingInfo)
        Log.d("WeatherIcon", iconUrl.toString())

        Picasso.get()
            .load(iconUrl)
            .into(weatherIconImageView)
    }

    /**
     * This method returns a greeting based on the time of day.
     * @return A greeting based on the time of day
     */
    private fun getTimeOfDayGreeting(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hourOfDay) {
            in 4..11 -> "Good morning!"  // 4 AM to 11 AM
            in 12..16 -> "Good afternoon!" // 12 PM to 4 PM
            else -> "Good evening!" // 5 PM to 3AM
        }
    }
    
    /**
     * This method creates a welcome greeting based on the current weather.
     * @param temperature The current temperature
     * @param weatherType The current weather type
     * @return A welcome greeting based on the current weather
     */
    private fun createWelcomeGreeting(temperature: Int? = null, weatherType: String? = null): String {
        return if (temperature!= null && weatherType != null) {
            if (weatherType == "Clear") {
                "It's $temperature°F and $weatherType"
            } else {
                "It's $temperature°F with $weatherType"
            }
        } else {
            "Welcome to HuskyHaze."
        }
    }

}