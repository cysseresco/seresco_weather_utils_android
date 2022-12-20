package seresco.weather.utils

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import seresco.weather.library.data.entity.SkyStatus
import seresco.weather.library.data.entity.SkyStatusPreference
import seresco.weather.library.ui.WeatherPredictionViewModel
import seresco.weather.library.utils.Constant
import seresco.weather.library.utils.MeteorologyUtils
import seresco.weather.library.utils.NotificationPreferences
import java.util.*

class MainActivity : AppCompatActivity(), OnMapReadyCallback, Constant {

    lateinit var googleMap: GoogleMap
    private val meteorologyUtils = MeteorologyUtils()
    private lateinit var weatherViewModel: WeatherPredictionViewModel
    private lateinit var notificationPreferences: NotificationPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMap()
        setupPreferences()
    }

    private fun setupPreferences() {
        notificationPreferences = NotificationPreferences(this)
    }

    private fun setUpMap() {
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.getMapAsync(this)
    }

    private fun setupView() {
        moveCamera()
        createNotificationChannel()

        googleMap.setOnMapClickListener {
            /*val intent = Intent(this, ReminderBroadcast::class.java)
            val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
            val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
            val timeAtButtonClick = System.currentTimeMillis()
            val tenSecondsInMillis = 1000 * 10

            val cal = Calendar.getInstance(Locale.getDefault())
            cal.apply {
                set(Calendar.DAY_OF_YEAR,get(Calendar.DAY_OF_YEAR)+1)
                set(Calendar.HOUR_OF_DAY, 21)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            alarmManager.set(AlarmManager.RTC_WAKEUP,
                        cal.timeInMillis,
                        pendingIntent)*/

            //meteorologyUtils.openOptionsSheet(supportFragmentManager, 33007)

//                weatherViewModel = ViewModelProvider(this)[WeatherPredictionViewModel::class.java]
//                weatherViewModel.getWeatherPredictionsPerMunicipality(15078)
//                setUpObservers()

            meteorologyUtils.openOptionsSheet(supportFragmentManager, 15078)
        }
    }

    private fun setupNotification() {
        val intent = Intent(this, ReminderBroadcast::class.java)
        val pendingIntent = PendingIntent.getBroadcast(this, 0, intent, 0)
        val alarmManager = getSystemService(ALARM_SERVICE) as AlarmManager
        val skyStatusContent = notificationPreferences.getSkyStatus(NOTIFICATION_PREFERENCES_SKY_STATUS)
        Log.e("hey! nanniiiii dos", skyStatusContent.toString())
        val daysToSet: Int = (skyStatusContent?.dayToSet ?: 0) - 1

        val cal = Calendar.getInstance(Locale.getDefault())
        cal.apply {
            Log.e("hey! nani --->", daysToSet.toString())
            set(Calendar.DAY_OF_YEAR,get(Calendar.DAY_OF_YEAR)+daysToSet)
            set(Calendar.HOUR_OF_DAY, 9)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        alarmManager.set(AlarmManager.RTC_WAKEUP,
            cal.timeInMillis,
            pendingIntent)

        //notificationPreferences.clearPreferences()
    }

    private fun setUpObservers() {
        weatherViewModel.weatherPrediction.observe(this, androidx.lifecycle.Observer {
            val xd = it.prediccion.dia.map { it.estadoCielo }
//            meteorologyUtils.setupMeteorologyNotifications(this, xd)
            var aux = 0
            Log.e("hey!! total", (xd.map { it.map { it.descripcion } }).toString())
            xd.forEach { list: List<SkyStatus> ->
                aux += 1
                list.forEach { skyStatus ->
                    if (skyStatus.descripcion == "Nuboso con lluvia") {
                        Log.e("hey!!!!", "$skyStatus and $aux")
                        if (skyStatus.periodo != "00-24") {
                            Log.e("hey!! skyStatus", skyStatus.toString())
                            Log.e("hey!! skyStatus", aux.toString())
                            val skyStatusPreferences = SkyStatusPreference("Cuidado! ${skyStatus.descripcion}", "Se predice ${skyStatus.descripcion} en el día de mañana", skyStatus.periodo, aux)
                            Toast.makeText(this, "Notificación programada en ${aux-1} días", Toast.LENGTH_SHORT).show()
                            notificationPreferences.saveSkyStatus(NOTIFICATION_PREFERENCES_SKY_STATUS, skyStatusPreferences)
                            setupNotification()
                        }
                    }
                }
            }
        })
    }

    private fun moveCamera() {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.361231, -5.848566), 20f))
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        setupView()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelName = CHANNEL_NAME
            val description = CHANNEL_DESCRIPTION
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, channelName, importance)
            channel.description = description

            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }
}