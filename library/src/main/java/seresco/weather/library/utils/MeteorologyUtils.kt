package seresco.weather.library.utils

import android.content.Context
import android.location.Geocoder
import android.util.Log
import androidx.fragment.app.FragmentManager
import seresco.weather.library.ui.options.OptionsBottomSheet
import seresco.weather.library.ui.precipitation.HumidityBottomSheet
import seresco.weather.library.ui.precipitation.PrecipitationBottomSheet
import seresco.weather.library.ui.weather.WeatherPredictionBottomSheet
import seresco.weather.library.ui.weather.WeatherTomorrowPredictionBottomSheet
import seresco.weather.library.ui.weather.WeatherWeeklyPredictionBottomSheet
import seresco.weather.library.ui.wind.WindPredictionBottomSheet
import java.util.*

class MeteorologyUtils: OptionsBottomSheet.OptionsSheetCallback {

    var supportFragmentManager: FragmentManager? = null

    fun openWeatherPredictionSheet(municipalityId: Int, supportFragmentManager: FragmentManager) {
        val trackingSheet = WeatherPredictionBottomSheet.newInstance(true, municipalityId)
        trackingSheet.show(supportFragmentManager, WeatherPredictionBottomSheet.TAG)
    }

    fun openMeteorologySheet(context: Context, meteorologyType: MeteorologyType, latitude: Double, longitude: Double, supportFragmentManager: FragmentManager) {
        val municipalityId = getMunicipalityId(context, latitude, longitude)
        openMeteorologySheetWithMunicipalityId(supportFragmentManager, meteorologyType, municipalityId)
    }

    private fun openMeteorologySheetWithMunicipalityId(supportFragmentManager: FragmentManager, meteorologyType: MeteorologyType, municipalityId: Int) {
        when (meteorologyType) {
            MeteorologyType.WEATHER_TODAY -> {
                val bottomSheet = WeatherPredictionBottomSheet.newInstance(true, municipalityId)
                bottomSheet.show(supportFragmentManager, WeatherPredictionBottomSheet.TAG)
            }
            MeteorologyType.WEATHER_TOMORROW -> {
                val bottomSheet = WeatherTomorrowPredictionBottomSheet.newInstance(true, municipalityId)
                bottomSheet.show(supportFragmentManager, WeatherTomorrowPredictionBottomSheet.TAG)
            }
            MeteorologyType.WEATHER_WEEKLY -> {
                val bottomSheet = WeatherWeeklyPredictionBottomSheet.newInstance(true, municipalityId)
                bottomSheet.show(supportFragmentManager, WeatherWeeklyPredictionBottomSheet.TAG)
            }
            MeteorologyType.PRECIPITATION -> {
                val bottomSheet = PrecipitationBottomSheet.newInstance(true, municipalityId)
                bottomSheet.show(supportFragmentManager, PrecipitationBottomSheet.TAG)
            }
            MeteorologyType.WIND -> {
                val bottomSheet = WindPredictionBottomSheet.newInstance(true, municipalityId)
                bottomSheet.show(supportFragmentManager, WindPredictionBottomSheet.TAG)
            }
            MeteorologyType.HUMIDITY -> {
                val bottomSheet = HumidityBottomSheet.newInstance(true, municipalityId)
                bottomSheet.show(supportFragmentManager, HumidityBottomSheet.TAG)
            }
        }
    }

    fun openWeatherPredictionSheet(context: Context, latitude: Double, longitude: Double, supportFragmentManager: FragmentManager) {
        val municipalityId = getMunicipalityId(context, latitude, longitude)
        val trackingSheet = WeatherPredictionBottomSheet.newInstance(true, municipalityId)
        trackingSheet.show(supportFragmentManager, WeatherPredictionBottomSheet.TAG)
    }

    fun openOptionsSheet(supportFragmentManager: FragmentManager, municipalityId: Int) {
        this.supportFragmentManager = supportFragmentManager
        val optionsSheet = OptionsBottomSheet.newInstance(true, this, municipalityId)
        optionsSheet.show(supportFragmentManager, OptionsBottomSheet.TAG)
    }

    private fun getMunicipalityId(context: Context, latitude: Double, longitude: Double): Int {
        val geocoder = Geocoder(context, Locale.getDefault())
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        val postalCode = addresses.first().postalCode;
        return postalCode.toInt()
    }

    override fun onOptionClicked(meteorologyType: MeteorologyType, municipalityId: Int) {
        Log.e("hey!!", "oneee")
        supportFragmentManager?.let {
            Log.e("hey!!", "twoo")
            openMeteorologySheetWithMunicipalityId(it, meteorologyType, municipalityId)
        }
    }

}

enum class MeteorologyType {
    WEATHER_TODAY, WEATHER_TOMORROW, WEATHER_WEEKLY, PRECIPITATION, WIND, HUMIDITY
}