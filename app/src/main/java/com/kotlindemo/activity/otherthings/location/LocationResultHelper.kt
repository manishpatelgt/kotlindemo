package com.kotlindemo.activity.otherthings.location

import android.location.Location


/**
 * Created by Manish Patel on 7/5/2019.
 */
object LocationResultHelper {

    fun getLocationResultText(locations: List<Location>): String {
        if (locations.isEmpty()) {
            return "Unknown"
        }
        val sb = StringBuilder()
        for (location in locations) {
            sb.append("(")
            sb.append(location.latitude)
            sb.append(", ")
            sb.append(location.longitude)
            sb.append(")")
            sb.append("\n")
        }
        return sb.toString()
    }
}