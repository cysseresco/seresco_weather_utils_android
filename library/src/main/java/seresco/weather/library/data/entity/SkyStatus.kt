package seresco.weather.library.data.entity

data class SkyStatus(
    val value: String,
    val periodo: String,
    val descripcion: String
)

data class SkyStatusPreference(
    val title: String,
    val body: String,
    val period: String?,
    val dayToSet: Int
)