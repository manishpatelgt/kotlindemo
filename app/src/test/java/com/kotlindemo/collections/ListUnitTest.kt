package com.kotlindemo.collections

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/05_Collections/01_List

class ListUnitTest {

    val systemUsers: MutableList<Int> = mutableListOf(1, 2, 3)        // 1
    val sudoers: List<Int> = systemUsers                              // 2

    fun addSudoer(newUser: Int) {                                     // 3
        systemUsers.add(newUser)
    }

    fun getSysSudoers(): List<Int> {                                  // 4
        return sudoers
    }

    @Test
    fun main() {

        println(getSysSudoers())

        addSudoer(4)                                                  // 5
        println("Tot sudoers: ${getSysSudoers().size}")               // 6
        getSysSudoers().forEach {                                      // 7
                i ->
            println("Some useful info on user $i")
        }

        // getSysSudoers().add(5) <- Error!
    }
}