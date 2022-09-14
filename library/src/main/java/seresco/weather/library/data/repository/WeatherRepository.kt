package seresco.weather.library.data.repository

import android.content.Context
import android.util.Log
import diegitsen.test.library.data.entity.SpecificPredictionResponse
import seresco.weather.library.data.datasource.OnWeatherRemoteReadyCallback
import seresco.weather.library.data.datasource.WeatherRemoteDatasource

class WeatherRepository()  {

    private val remoteDatasource = WeatherRemoteDatasource()

    fun getWeatherPrediction(municipalityId: Int, onWeatherRepositoryReadyCallback: OnWeatherRepositoryReadyCallback){
        remoteDatasource.getWeatherPrediction(municipalityId, object :
            OnWeatherRemoteReadyCallback {
            override fun onRemoteWeatherPredictionsDataReady(predictionData: SpecificPredictionResponse?) {
                onWeatherRepositoryReadyCallback.onDataReady(predictionData)
            }
        })
    }
}

interface OnWeatherRepositoryReadyCallback {
    fun onDataReady(predictionData: SpecificPredictionResponse?)
}