Seresco Weather Utils Android
=======

A weather library for Android using AEMET data

<p float="left">
  <img src="art/img_weather_weekly.jpg" width="200" height="400">
  <img src="art/img_weather_tomorrow.jpg" width="200" height="400">
  <img src="art/img_precipitation.jpg" width="200" height="400">
  <img src="art/img_weather_today.jpg" width="200" height="400">
</p>


Usage
--------

e.g. Displaying Weekly Weather Info

```kotlin
private val meteorologyUtils = MeteorologyUtils()

fun openWeatherWeeklySheet() {
    meteorologyUtils.openMeteorologySheet(this, MeteorologyType.WEATHER_WEEKLY, latitude, longitude, supportFragmentManager)
}
```
