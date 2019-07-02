package com.kotlindemo.specialclasses

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/03_special_classes/02_Enum

class EnumClassUnitTest {

    enum class State {
        IDLE, RUNNING, FINISHED                           // 1
    }

    enum class Color(val rgb: Int) {                      // 1
        RED(0xFF0000),                                    // 2
        GREEN(0x00FF00),
        BLUE(0x0000FF),
        YELLOW(0xFFFF00);

        fun containsRed() = (this.rgb and 0xFF0000 != 0)  // 3
    }

    @Test
    fun main() {
        //1st case
        val state = State.RUNNING                         // 2
        val message = when (state) {                      // 3
            State.IDLE -> "It's idle"
            State.RUNNING -> "It's running"
            State.FINISHED -> "It's finished"
        }
        println(message)

        println("----------------------------------------------")
        println("----------------------------------------------")

        //2nd case
        val red = Color.RED
        println(red)                                      // 4
        println(red.containsRed())                        // 5
        println(Color.BLUE.containsRed())

        val blue = Color.BLUE
        println(blue)

    }
}