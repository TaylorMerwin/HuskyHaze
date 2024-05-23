package edu.tacoma.uw.huskyhaze

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.LinearInterpolator
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.squareup.picasso.Picasso
import edu.tacoma.uw.huskyhaze.network.WeatherService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class MainActivity : AppCompatActivity() {

    private val REQUEST_MAPS_ACTIVITY = 1
    private var latitude = 47.24 // Default UWT latitude
    private var longitude = -122.43 // Default UWT longitude
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val weatherButton = findViewById<Button>(R.id.weatherButton)
        val newsButton = findViewById<Button>(R.id.newsButton)
        val settingsButton = findViewById<ImageButton>(R.id.settingsButton)
        val aboutUsButton = findViewById<Button>(R.id.aboutUsBtnMain)
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
            val translateAnimator = ObjectAnimator.ofFloat(
                settingsButton,
                "translationX",
                0f, -(getScreenWidth().toFloat()-settingsButton.width.toFloat()-50)
            )
            translateAnimator.duration = 1000
            val rotateAnimator = ObjectAnimator.ofFloat(settingsButton, "rotation", 0f, -540f)
            rotateAnimator.duration = 1000
            val animatorSet = AnimatorSet()
            animatorSet.playTogether(translateAnimator, rotateAnimator)
            animatorSet.start()
            animatorSet.addListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator) {
                    TODO("Not yet implemented")
                }
                override fun onAnimationEnd(animation: Animator) {
                    val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                    startActivity(intent)
                }
                override fun onAnimationCancel(animation: Animator) {
                    TODO("Not yet implemented")
                }
                override fun onAnimationRepeat(animation: Animator) {
                    TODO("Not yet implemented")
                }
            })
        }
        aboutUsButton.setOnClickListener {
            val intent = Intent(this, AboutUsActivity::class.java)
            startActivity(intent)
        }
        fetchCurrentWeather()
    }
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
    private fun getScreenWidth(): Int {
        return resources.displayMetrics.widthPixels
    }

    private fun fetchCurrentWeather() {
        val weatherService = WeatherService.create()
        val apiKey = getString(R.string.open_weather_api_key)
        // UWT coordinates
//        val latitude = 47.24
//        val longitude = -122.43
        var currentTemp = 0.0
        var currentWeather = "unknown"

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val response =
                    weatherService.getCurrentWeatherByCoordinates(latitude, longitude, apiKey)
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val weatherData = response.body()
                        if (weatherData != null) {
                            currentTemp = weatherData.current.temp;
                            currentWeather = weatherData.current.weather[0].main
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

    private fun displayWeather(temperature: Double?, weatherType: String?, iconUrl: String?) {
        val greeting = createWelcomeGreeting(temperature, weatherType)
        val greetingTextView = findViewById<TextView>(R.id.GreetingTextView)
        val weatherIconImageView = findViewById<ImageView>(R.id.weatherIconImageView)
        greetingTextView.text = greeting
        Log.d("WeatherGreeting", greeting)
        Log.d("WeatherIcon", iconUrl.toString())

        Picasso.get()
            .load(iconUrl)
            .into(weatherIconImageView)
    }

    private fun getTimeOfDayGreeting(): String {
        val calendar = Calendar.getInstance()
        val hourOfDay = calendar.get(Calendar.HOUR_OF_DAY)

        return when (hourOfDay) {
            in 4..11 -> "morning"  // 4 AM to 11 AM
            in 12..16 -> "afternoon" // 12 PM to 4 PM
            else -> "evening" // 5 PM to 3AM
        }
    }

    private fun createWelcomeGreeting(temperature: Double? = null, weatherType: String? = null): String {
        val timeGreeting = getTimeOfDayGreeting()
        return if (temperature!= null && weatherType != null) {
            "Good $timeGreeting! It's $temperature°F with $weatherType in Tacoma."
        } else {
            "Good $timeGreeting! Welcome to HuskyHaze."
        }
    }

}