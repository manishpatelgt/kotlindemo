package com.kotlindemo.higherorderfunctions

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/04_functional/01_Higher-Order%20Functions

class HigherOrderFunUnitTest {

    fun calculate(x: Int, y: Int, operation: (Int, Int) -> Int): Int {  // 1
        return operation(x, y)                                          // 2
    }

    fun sum(x: Int, y: Int) = x + y

    fun sub(x: Int, y: Int) = x - y

    fun operation(): (Int) -> Int {                                     // 1
        return ::square
    }

    fun square(x: Int) = x * x

    @Test
    fun main() {

        //case1: Taking Functions as Parameters
        val sumResult = calculate(4, 5, ::sum)
        val subResult = calculate(4, 5, ::sub) // 4
        val mulResult = calculate(4, 5) { a, b -> a * b }               // 5
        println("sumResult $sumResult, mulResult $mulResult subResult $subResult")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case2: Returning Functions
        val func = operation()                                          // 3
        println(func(2))

    }
}