package com.kotlindemo.controlflow

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/02_control_flow/04_Equality%20Checks

//Kotlin uses == for structural comparison and === for referential comparison.

class EqualityCheckUnitTest {

    @Test
    fun main() {

        val authors = setOf("Shakespeare", "Hemingway", "Twain")
        val writers = setOf("Twain", "Shakespeare", "Hemingway")

        println(authors == writers)   // 1
        println(authors === writers)  // 2
    }
}