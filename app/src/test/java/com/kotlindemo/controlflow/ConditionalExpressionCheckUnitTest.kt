package com.kotlindemo.controlflow

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/02_control_flow/05_Conditional%20expression

class ConditionalExpressionCheckUnitTest {

    fun max(a: Int, b: Int) = if (a > b) a else b

    @Test
    fun main() {

        println(max(99, -42))
        println(max(1, 5))
    }
}