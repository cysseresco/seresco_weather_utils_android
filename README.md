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

Installation
--------
Add in the settings.gradle at the end of repositories:
```groovy
repositories {
	maven { url 'https://jitpack.io' }
}
```
And add the dependencies
```groovy
dependencies {
    // Utilities for Maps SDK for Android (requires Google Play Services) 
    implementation 'com.google.maps.android:android-maps-utils:2.4.0'
    implementation 'com.github.cysseresco:seresco_weather_utils_android:1.0'
}
```
