package com.kotlindemo.scopefuncations

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/06_scope_functions/02_run
class RunScopeFunUnitTest {

    @Test
    fun main() {

        fun getNullableLength(ns: String?) {
            println("for \"$ns\":")
            ns?.run {
                // 1
                println("\tis empty? " + isEmpty())                    // 2
                println("\tlength = $length")
                length                                                 // 3
            }
        }

        getNullableLength(null)
        getNullableLength("")
        getNullableLength("some string with Kotlin")
        getNullableLength("My Testing scope")

    }
}