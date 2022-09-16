package seresco.weather.utils

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import seresco.weather.library.utils.MeteorologyType
import seresco.weather.library.utils.MeteorologyUtils

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    lateinit var googleMap: GoogleMap
    private val meteorologyUtils = MeteorologyUtils()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setUpMap()
    }

    private fun setUpMap() {
        (supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?)?.getMapAsync(this)
    }

    private fun setupView() {
        moveCamera()

        googleMap.setOnMapClickListener {
            meteorologyUtils.openOptionsSheet(supportFragmentManager, 33007)
           // googleMap.clear()
           // meteorologyUtils.openMeteorologySheet(this, MeteorologyType.WEATHER_TOMORROW, it.latitude, it.longitude, supportFragmentManager)
        }
    }

    private fun moveCamera() {
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(43.361231, -5.848566), 20f))
    }

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        setupView()
    }
}