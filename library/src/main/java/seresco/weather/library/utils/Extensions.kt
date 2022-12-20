package seresco.weather.library.utils

import seresco.weather.library.R

class Extensions {
    fun getWeatherIcon(weatherSkyStatus: String): Int {
        return when {
            weatherSkyStatus.contains("Despejado") -> {
                R.drawable.ic_clear
            }
            weatherSkyStatus.contains("nuboso") -> {
                R.drawable.ic_cloud
            }
            weatherSkyStatus.contains("Nubes") -> {
                R.drawable.ic_high_cloudy
            }
            weatherSkyStatus.contains("lluvia") -> {
                R.drawable.ic_rainy
            }
            weatherSkyStatus.contains("tormenta") -> {
                R.drawable.storm
            }
            else -> R.drawable.ic_rainy
        }
    }
}