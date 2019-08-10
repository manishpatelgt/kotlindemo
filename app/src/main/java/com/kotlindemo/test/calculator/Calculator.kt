package com.kotlindemo.test.calculator

/**
 * Created by Manish Patel on 8/10/2019.
 */
class Calculator(private val operators: Operators) {

    fun addTwoNumbers(a: Int, b: Int): Int = operators.add(a, b)

    fun subtractTwoNumbers(a: Int, b: Int): Int = operators.subtract(a, b)

    fun multiplyTwoNumbers(a: Int, b: Int): Int = operators.multiply(a, b)

    fun divideTwoNumbers(a: Int, b: Int): Int = operators.divide(a, b)
}