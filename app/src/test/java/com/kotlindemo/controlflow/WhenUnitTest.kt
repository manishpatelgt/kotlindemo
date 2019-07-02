package com.kotlindemo.controlflow

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

//https://play.kotlinlang.org/byExample/02_control_flow/01_When

class WhenUnitTest {

    fun cases(obj: Any) {                                // 1
        when (obj) {
            1 -> println("One")                          // 2
            "Hello" -> println("Greeting")               // 3
            is Long -> println("Long")                   // 4
            !is String -> println("Not a string")        // 5
            else -> println("Unknown")                   // 6
        }
    }

    fun whenAssign(obj: Any): Any {
        val result = when (obj) {                   // 1
            1 -> "one"                              // 2
            "Hello" -> 1                            // 3
            is Long -> false                        // 4
            else -> 42                              // 5
        }
        return result
    }

    @Test
    fun main() {

        //1st case
        cases("Hello")
        cases(1)
        cases(0L)
        cases(MyClass())
        cases("hello")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //2nd case
        println(whenAssign("Hello"))
        println(whenAssign(3.4))
        println(whenAssign(1))
        println(whenAssign(0L))
        println(whenAssign(MyClass()))
    }

    class MyClass
}