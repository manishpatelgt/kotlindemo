package com.kotlindemo.introduction

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

class OperatorFunctionsUnitTest {

    @Test
    fun main() {

        operator fun Int.times(str: String) = str.repeat(this)       // 1
        println(2 * "Bye")                                          // 2
        println(10 * "Test ")

        operator fun String.get(range: IntRange) = substring(range)  // 3
        val str = "Always forgive your enemies; nothing annoys them so much."
        println(str[0..14])
        println(str[0..5])


        fun printAll(vararg messages: String) {                            // 1
            for (m in messages) println(m)
        }

        printAll("Hello", "Hallo", "Salut", "Hola", "你好")                 // 2

        fun printAllWithPrefix(vararg messages: String, prefix: String) {  // 3
            for (m in messages) println(prefix + m)
        }

        printAllWithPrefix(
            "Hello", "Hallo", "Salut", "Hola", "你好",
            prefix = "Greeting: "                                          // 4
        )

        fun log(vararg entries: String) {
            printAll(*entries)                                             // 5
        }
    }
}