package com.kotlindemo.introduction

import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */

//https://play.kotlinlang.org/byExample/01_introduction/01_Hello%20world
class FunctionsUnitTest {

    fun printMessage(message: String): Unit {                               // 1
        println(message)
    }

    fun printMessageWithPrefix(message: String, prefix: String = "Info") {  // 2
        println("[$prefix] $message")
    }

    fun sum(x: Int, y: Int): Int {                                          // 3
        return x + y
    }

    fun multiply(x: Int, y: Int) = x * y                                    // 4

    @Test
    fun main() {
        printMessage("Hello")                                               // 5
        printMessageWithPrefix("Hello", "Log")                              // 6
        printMessageWithPrefix("Hello")                                     // 7
        printMessageWithPrefix(prefix = "Log", message = "Hello")           // 8
        println(sum(1, 2))                                                  // 9
    }
}
