Seresco Weather Utils Android
=======

A weather library for Android using AEMET data

<p float="left">
  <img src="art/img_weather_weekly.png" width="200" height="450">
  <img src="art/img_weather_tomorrow.png" width="200" height="450">
  <img src="art/img_precipitation.png" width="200" height="450">
  <img src="art/img_weather_today.png" width="200" height="450">
</p>


Usage
--------

e.g. Displaying Weekly Weather Info

```kotlin
private val meteorologyUtils = MeteorologyUtils()

func openWeatherWeeklySheet() {
    meteorologyUtils.openMeteorologySheet(this, MeteorologyType.WEATHER_WEEKLY, latitude, longitude, supportFragmentManager)
}
```
