package com.kotlindemo.introduction.Inheritance

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

//https://play.kotlinlang.org/byExample/01_introduction/07_Inheritance
//Inheritance with Parameterized Constructor
class InheritanceUnitTest2 {

    open class Tiger(var origin: String) {
        fun sayHello() {
            println("A tiger from $origin says: grrhhh!")
        }
    }

    class SiberianTiger : Tiger("Siberia")

    @Test
    fun main() {

        val tiger: Tiger =
            SiberianTiger()
        tiger.sayHello()

        println("----------------------------------------------")
        println("----------------------------------------------")

        val tiger2 = Tiger("Abu")
        tiger2.sayHello()

        println("----------------------------------------------")
        println("----------------------------------------------")

        tiger2.origin = "India"
        tiger2.sayHello()
    }
}