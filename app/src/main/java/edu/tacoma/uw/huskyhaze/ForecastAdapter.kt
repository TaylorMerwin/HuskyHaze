/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.tacoma.uw.huskyhaze.models.DateUtils.formatToDayMonth
import edu.tacoma.uw.huskyhaze.models.DateUtils.toDate
import edu.tacoma.uw.huskyhaze.models.WeatherData
import java.util.Calendar
import kotlin.math.roundToInt

class ForecastAdapter(private val forecastList: List<WeatherData.DailyData>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>(){

    class ForecastViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val minTempTextView: TextView = itemView.findViewById(R.id.minTempTextView)
        val maxTempTextView: TextView = itemView.findViewById(R.id.maxTempTextView)
        val weatherTypeTextView: TextView = itemView.findViewById(R.id.weatherTypeTextView)
        val weatherTypeImageView: ImageView = itemView.findViewById(R.id.weatherTypeImageView)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):ForecastViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.daily_forecast_item, parent, false)
        return ForecastViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ForecastViewHolder, position: Int) {
        val currentForecast = forecastList[position]

        holder.dateTextView.text = currentForecast.dt.toDate().formatToDayMonth()
        holder.minTempTextView.text = "Min: " + currentForecast.temp.min.roundToInt().toString() + "°F"
        holder.maxTempTextView.text = "Max: " + currentForecast.temp.max.roundToInt().toString() + "°F"
        holder.weatherTypeTextView.text = checkForAtmosphere(currentForecast.weather[0].main)
        holder.weatherTypeImageView.setImageResource(getWeatherIcon(currentForecast.weather[0].main))
    }

    private fun getWeatherIcon(weatherType: String): Int {
        return when (weatherType) {
            "Clear" -> getTimeOfDayForClear()
            in "Clouds" -> R.drawable.ic_clouds
            in "Atmosphere" -> R.drawable.ic_atmosphere
            in "Snow" -> R.drawable.ic_snow
            in "Rain" -> R.drawable.ic_rain
            in "Drizzle" -> R.drawable.ic_drizzle
            in "Thunderstorm" -> R.drawable.ic_thunderstorm
            else -> getTimeOfDayForClear()
        }
    }

    private fun checkForAtmosphere(weatherType: String): String {
        return when (weatherType) {
            "Atmosphere" -> "Haze"
            else -> weatherType
        }
    }

    private fun getTimeOfDayForClear(): Int {
        val hourOfDay = Calendar.getInstance().get(Calendar.HOUR_OF_DAY)

        return when (hourOfDay) {
            in 6..18 -> R.drawable.ic_clear_day  // 6 AM to 6 PM
            else -> R.drawable.ic_clear_night // 7 PM to 5AM
        }
    }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}