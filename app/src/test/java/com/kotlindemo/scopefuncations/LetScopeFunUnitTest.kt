package com.kotlindemo.scopefuncations

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/06_scope_functions/01_let

class LetScopeFunUnitTest {

    fun printNonNull(str: String?) {
        println("Printing \"$str\":")

        str?.let {
            // 4
            print("\t")
            customPrint(it)
            println()
        }
    }

    fun customPrint(message: String) {
        println("${message.toUpperCase()}")
    }

    @Test
    fun main() {

        val empty = "test".let {
            // 1
            customPrint(it)                    // 2
            it.isEmpty()                       // 3
        }
        println(" is empty: $empty")

        printNonNull(null)
        printNonNull("my string")
    }
}