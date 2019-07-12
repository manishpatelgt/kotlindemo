package com.kotlindemo.introduction.Inheritance

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/01_introduction/07_Inheritance

class InheritanceUnitTest {

    @Test
    fun main() {
        val dog: Dog = Yorkshire()
        dog.sayHello()

        println("----------------------------------------------")
        println("----------------------------------------------")

        val dog1 = Dog()
        dog1.sayHello()
    }

    open class Dog {                // 1
        open fun sayHello() {       // 2
            println("wow wow!")
        }
    }

    class Yorkshire : Dog() {       // 3
        override fun sayHello() {   // 4
            println("wif wif!")
        }
    }


}