package com.kotlindemo.appconstants

import com.kotlindemo.activity.architecture.databinding2.WeatherData

/**
 * Created by Manish Patel on 5/24/2019.
 */
object Consts {
    val pi: Double = 3.14

    fun getWeatherList(): List<WeatherData> {

        return listOf(
            WeatherData(
                "Saint Petersburg",
                "10 °C",
                "Cloudy with rain and possible thunderstorms",
                "http://www.saint-petersburg.com/images/weather/weather-in-st-petersburg.jpg"
            ),
            WeatherData(
                "Munich",
                "23 °C",
                "Partly sunny and delightful",
                "https://cdn.muenchen-p.de/fl_progressive,q_65/.imaging/stk/responsive/teaser300/dms/shutterstock-2016/sehenswuerdigkeiten/friedensengel-aussicht-hp/document/friedensengel-aussicht-hp.jpg"
            ),
            WeatherData(
                "Novosibirsk",
                "9 °C",
                "Mostly cloudy",
                "https://cdn.britannica.com/s:500x350/92/144492-004-3AF8349B.jpg"
            ),
            WeatherData(
                "Barcelona",
                "15 °C",
                "Partly cloudy",
                "https://resources.stuff.co.nz/content/dam/images/1/g/b/d/l/y/image.related.StuffLandscapeSixteenByNine.710x400.1mhfsh.png/1509346105878.jpg"
            ),
            WeatherData(
                "London",
                "11 °C",
                "Partly cloudy",
                "https://cdn.londonandpartners.com/visit/general-london/areas/river/76709-640x360-houses-of-parliament-and-london-eye-on-thames-from-above-640.jpg"
            )
        )

    }
}