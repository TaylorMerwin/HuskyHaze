package edu.tacoma.uw.huskyhaze

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import edu.tacoma.uw.huskyhaze.models.DateUtils.formatToDayMonth
import edu.tacoma.uw.huskyhaze.models.DateUtils.toDate
import edu.tacoma.uw.huskyhaze.models.WeatherData

class ForecastAdapter(private val forecastList: List<WeatherData.DailyData>) :
    RecyclerView.Adapter<ForecastAdapter.ForecastViewHolder>(){

    class ForecastViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
        val minTempTextView: TextView = itemView.findViewById(R.id.minTempTextView)
        val maxTempTextView: TextView = itemView.findViewById(R.id.maxTempTextView)
        val weatherTypeTextView: TextView = itemView.findViewById(R.id.weatherTypeTextView)
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
            holder.minTempTextView.text = "Min: " + currentForecast.temp.min.toString() + "°F"
            holder.maxTempTextView.text = "Max: " + currentForecast.temp.max.toString() + "°F"
            holder.weatherTypeTextView.text = currentForecast.weather[0].main
        }

    override fun getItemCount(): Int {
        return forecastList.size
    }
}