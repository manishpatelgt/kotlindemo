package com.kotlindemo.collections

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/05_Collections/04_map

class mapExtenstionFunUnitTest {

    @Test
    fun main() {

        val numbers = listOf(1, -2, 3, -4, 5, -6)     // 1

        val doubled = numbers.map { x -> x * 2 }      // 2

        val tripled = numbers.map { it * 3 }          // 3

        println(numbers)

        doubled.forEach {                                      // 7
                i ->
            println(" $i")
        }

        println("----------------------------------------------")
        println("----------------------------------------------")

        tripled.forEach {                                      // 7
                i ->
            println(" $i")
        }

    }
}