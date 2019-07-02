package com.kotlindemo.controlflow

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/02_control_flow/03_Ranges
class RangeUnitTest {

    @Test
    fun main() {

        //case1
        for (i in 0..3) {             // 1
            print(i)
        }
        print(" ")

        for (i in 2..8 step 2) {      // 2
            print(i)
        }
        print(" ")

        for (i in 3 downTo 0) {      // 3
            print(i)
        }
        print(" ")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case2
        for (c in 'a'..'d') {        // 1
            print(c)
        }
        print(" ")

        for (c in 'z' downTo 's' step 2) { // 2
            print(c)
        }
        print(" ")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case3
        val x = 5
        if (x in 1..10) {            // 1
            print(x)
        }
        print(" ")

        if (x !in 1..4) {            // 2
            print(x)
        }
    }
}