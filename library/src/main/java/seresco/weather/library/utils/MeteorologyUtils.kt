package seresco.weather.library.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.location.Geocoder
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.FragmentManager
import seresco.weather.library.data.entity.SkyStatus
import seresco.weather.library.data.entity.SkyStatusPreference
import seresco.weather.library.ui.options.OptionsBottomSheet
import seresco.weather.library.ui.precipitation.HumidityBottomSheet
import seresco.weather.library.ui.precipitation.PrecipitationBottomSheet
import seresco.weather.library.ui.weather.WeatherPredictionBottomSheet
import seresco.weather.library.ui.weather.WeatherTomorrowPredictionBottomSheet
import seresco.weather.library.ui.weather.WeatherWeeklyPredictionBottomSheet
import seresco.weather.library.ui.wind.WindPredictionBottomSheet
import java.util.*

class MeteorologyUtils: OptionsBottomSheet.OptionsSheetCallback, Constant {

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

    fun setupMeteorologyNotifications(context: Context, skyStatusData: List<List<SkyStatus>>) {
        var aux = 0
        skyStatusData.forEach { list: List<SkyStatus> ->
            aux += 1
            list.forEach { skyStatus ->
                if (skyStatus.descripcion == "Nuboso con lluvia") {
                    Log.e("hey!!!!", "$skyStatus and $aux")
                    if (skyStatus.periodo != "00-24") {
                        Log.e("hey!! skyStatus", skyStatus.toString())
                        Log.e("hey!! skyStatus", aux.toString())
                        val skyStatusPreferences = SkyStatusPreference("Cuidado! ${skyStatus.descripcion}", "Se predice ${skyStatus.descripcion} en el día de mañana", skyStatus.periodo, aux)
                        Toast.makeText(context, "Notificación programada en ${aux-1} días", Toast.LENGTH_SHORT).show()
                        val notificationPreferences = NotificationPreferences(context)
                        notificationPreferences.saveSkyStatus(NOTIFICATION_PREFERENCES_SKY_STATUS, skyStatusPreferences)
                        setupNotification(context)
                    }
                }
            }
        }
    }

    private fun setupNotification(context: Context) {
//        val intent = Intent(context, ReminderBroadcast::class.java)
//        val pendingIntent = PendingIntent.getBroadcast(context, 0, intent, 0)
//        val notificationPreferences = NotificationPreferences(context)
//        val alarmManager = context.getSystemService(AppCompatActivity.ALARM_SERVICE) as AlarmManager
//        val skyStatusContent = notificationPreferences.getSkyStatus(NOTIFICATION_PREFERENCES_SKY_STATUS)
//        Log.e("hey! nanniiiii dos", skyStatusContent.toString())
//        val daysToSet: Int = (skyStatusContent?.dayToSet ?: 0) - 1
//
//        val cal = Calendar.getInstance(Locale.getDefault())
//        cal.apply {
//            Log.e("hey! nani --->", daysToSet.toString())
//            set(Calendar.DAY_OF_YEAR,get(Calendar.DAY_OF_YEAR)+daysToSet)
//            set(Calendar.HOUR_OF_DAY, 9)
//            set(Calendar.MINUTE, 0)
//            set(Calendar.SECOND, 0)
//            set(Calendar.MILLISECOND, 0)
//        }
//
//        alarmManager.set(
//            AlarmManager.RTC_WAKEUP,
//            cal.timeInMillis,
//            pendingIntent)

        //notificationPreferences.clearPreferences()
    }

    override fun onOptionClicked(meteorologyType: MeteorologyType, municipalityId: Int) {
        supportFragmentManager?.let {
            openMeteorologySheetWithMunicipalityId(it, meteorologyType, municipalityId)
        }
    }

}

enum class MeteorologyType {
    WEATHER_TODAY, WEATHER_TOMORROW, WEATHER_WEEKLY, PRECIPITATION, WIND, HUMIDITY
}