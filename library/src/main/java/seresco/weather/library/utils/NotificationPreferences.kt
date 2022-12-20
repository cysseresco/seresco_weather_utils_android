package seresco.weather.library.utils

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import seresco.weather.library.data.entity.SkyStatusPreference

class NotificationPreferences (var context: Context) {
    var pref: SharedPreferences? = null

    fun saveSkyStatus(key: String, value: SkyStatusPreference){
        val editor = pref!!.edit()
        val jsonString = Gson().toJson(value)
        editor.putString(key, jsonString).apply()
    }

    fun getSkyStatus(key: String): SkyStatusPreference? {
        val jsonString = pref!!.getString(key, "")
        return Gson().fromJson(jsonString, object: TypeToken<SkyStatusPreference>(){}.type)
    }

    fun clearPreferences(){
        pref!!.edit().clear().apply()
    }

    init {
        pref = context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }
}