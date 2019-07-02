package com.kotlindemo.scopefuncations

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/06_scope_functions/04_apply

class ApplyScopeFunUnitTest {

    data class Person(var name: String, var age: Int, var about: String) {
        constructor() : this("", 0, "")
    }

    @Test
    fun main() {
        val jake = Person()                                     // 1
        val stringDescription = jake.apply {
            // 2
            name = "Manish"                                       // 3
            age = 29
            about = "Android developer"
        }.toString()

        println(stringDescription)
    }
}