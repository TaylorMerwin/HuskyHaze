/**
 * Team 3 - TCSS 450 - Spring 2024
 */
package edu.tacoma.uw.huskyhaze.models

class WeatherData {
    data class WeatherData(val current: CurrentData, val daily: List<DailyData>)
    data class CurrentData(val temp: Double, val weather: List<CurrentWeatherType>)
    data class CurrentWeatherType(val main: String, val description: String, val icon: String)
    data class DailyData(val dt: Long, val temp: TempData, val weather: List<WeatherType>)
    data class TempData(val day: Double, val min: Double, val max: Double)
    data class WeatherType(val main: String, val description: String)
}