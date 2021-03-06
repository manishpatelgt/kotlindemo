package com.kotlindemo.specialclasses

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/03_special_classes/01_Data%20classes

class DataClassUnitTest {

    data class User(val name: String, val id: Int)

    @Test
    fun main() {

        val user = User("Alex", 1)
        println(user)                                          // 2

        val secondUser = User("Alex", 1)
        val thirdUser = User("Max", 2)

        println("user == secondUser: ${user == secondUser}")   // 3
        println("user == thirdUser: ${user == thirdUser}")

        println(user.hashCode())                               // 4
        println(thirdUser.hashCode())

        // copy() function
        println(user.copy())                                   // 5
        println(user.copy("Max"))                              // 6
        println(user.copy(id = 2))                             // 7

        println("name = ${user.component1()}")                 // 8
        println("id = ${user.component2()}")

    }

}