package com.kotlindemo.introduction

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/01_introduction/03_Variables

class VariablesUnitTest {

    @Test
    fun main() {

        var a: String = "manish"
        println(a)

        a = "mdp"
        println(a)

        val b: Int = 1
        println(b)

        val c = 2
        println(c)

        val d: Int  // 1

        if (5 > 1) {
            d = 5   // 2
        } else {
            d = 6   // 2
        }

        println(d) // 3
    }
}