package com.kotlindemo.introduction

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/01_introduction/02_Functions
class InfixFunctionsUnitTest {

    @Test
    fun main() {

        infix fun Int.times(str: String) = str.repeat(this)        // 1
        println(4 times "Bye")                                    // 2

        val pair = "Ferrari" to "Katrina"                          // 3
        println(pair)

        infix fun String.onto(other: String) = Pair(this, other)   // 4
        val myPair = "McLaren" onto "Lucas"
        println(myPair)

        val sophia = Person("Sophia")
        val claudia = Person("Claudia")
        sophia likes claudia                                       // 5


        //member function
        Sample().foo()
    }

    class Person(val name: String) {
        val likedPeople = mutableListOf<Person>()
        infix fun likes(other: Person) {
            likedPeople.add(other)
        }  // 6
    }

    class Sample() {
        fun foo() {
            print("Foo")
        }
    }
}