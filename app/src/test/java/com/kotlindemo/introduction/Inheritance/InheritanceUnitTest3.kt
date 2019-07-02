package com.kotlindemo.introduction.Inheritance

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

//https://play.kotlinlang.org/byExample/01_introduction/07_Inheritance
//Inheritance with Parameterized Constructor

class InheritanceUnitTest3 {

    open class Lion(val name: String, val origin: String) {
        fun sayHello() {
            println("$name, the lion from $origin says: graoh!")
        }
    }

    class Asiatic(name: String) : Lion(name = name, origin = "India") // 1

    @Test
    fun main() {

        val lion: Lion =
            Asiatic("Rufo")                              // 2
        lion.sayHello()

        println("----------------------------------------------")
        println("----------------------------------------------")

        val lion2 = Lion("elephant", "Abu")
        lion2.sayHello()

    }
}