package edu.tacoma.uw.huskyhaze.models

class WeatherData {
    data class WeatherData(val main: MainData)
    data class MainData(val temp: Double)
}