package com.kotlindemo.collections

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/05_Collections/04_filter
class FilterUnitTest {

    @Test
    fun main() {
        val numbers = listOf(1, -2, 3, -4, 5, -6)      // 1
        val positives = numbers.filter { x -> x > 0 }  // 2
        val negatives = numbers.filter { it < 0 }      // 3

        println(numbers)

        positives.forEach {                                      // 7
                i ->
            println("Positive $i")
        }

        negatives.forEach {                                      // 7
                i ->
            println("Negative $i")
        }
    }
}