package diegitsen.test.library.data.entity

import seresco.weather.library.data.entity.SkyStatus

data class Day(
    val estadoCielo: List<SkyStatus>,
    val probPrecipitacion: List<ClimatologyItem>,
    val temperatura: Temperature,
    val viento: List<WindItem>,
    val humedadRelativa: RelativeHumidity
)