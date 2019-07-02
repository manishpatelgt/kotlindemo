package com.kotlindemo.activity.databinding2

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.kotlindemo.R
import com.kotlindemo.appconstants.Consts
import com.kotlindemo.application.ParentActivity
import kotlinx.android.synthetic.main.activity_databinding_two_way.*

/**
 * Created by Manish Patel on 5/23/2019.
 */
class OtherActivity : ParentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        setSupportActionBar(toolbar as Toolbar?)
        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)

        val items = Consts.getWeatherList()
        /*val items = listOf(
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
        )*/

        val recyclerView: RecyclerView = findViewById(R.id.weather_list)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = WeatherAdapter(items)
    }

    class WeatherAdapter(
        val data: List<WeatherData>
    ) : RecyclerView.Adapter<WeatherViewHolder>() {

        override fun onBindViewHolder(holder: WeatherViewHolder, position: Int) {
            holder?.let {
                val weatherData = data[position]
                it.bind(weatherData)
                it.itemView.setOnClickListener {
                    //itemClick(weatherData)
                }
            }

        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WeatherViewHolder {
            val layoutInflater = LayoutInflater.from(parent.context)
            val binding: ViewDataBinding =
                DataBindingUtil.inflate(layoutInflater, R.layout.weather_row, parent, false)

            return WeatherViewHolder(binding)
        }

        override fun getItemCount(): Int = data.size
    }

    class WeatherViewHolder(val binding: ViewDataBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Any) {
            binding.setVariable(BR.data, data)
            binding.executePendingBindings()
        }
    }
}