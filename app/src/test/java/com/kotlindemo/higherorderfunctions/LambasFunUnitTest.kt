package com.kotlindemo.higherorderfunctions

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/04_functional/02_Lambdas

class LambasFunUnitTest {

    // All examples create a function object that performs upper-casing.
    // So it's a function from String to String

    val upperCase1: (String) -> String = { str: String -> str.toUpperCase() } // 1

    val upperCase2: (String) -> String = { str -> str.toUpperCase() }         // 2

    val upperCase3 = { str: String -> str.toUpperCase() }                     // 3

    // val upperCase4 = { str -> str.toUpperCase() }                          // 4

    val upperCase5: (String) -> String = { it.toUpperCase() }                 // 5

    val upperCase6: (String) -> String = String::toUpperCase                  // 6

    @Test
    fun main() {
        println(upperCase2("hello"))
        println(upperCase1("hello"))
        println(upperCase3("hello"))
        println(upperCase5("hello"))
        println(upperCase6("hello"))
    }
}