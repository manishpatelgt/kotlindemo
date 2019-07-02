package com.kotlindemo.scopefuncations

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/06_scope_functions/05_also

class AlsocopeFunUnitTest {

    data class Person(var name: String, var age: Int, var about: String) {
        constructor() : this("", 0, "")
    }

    fun writeCreationLog(p: Person) {
        println("A new person ${p.name} was created.")
    }

    @Test
    fun main() {
        val jake = Person("Jake", 30, "Android developer")   // 1
            .also {
                // 2
                writeCreationLog(it)                         // 3
            }

        println(jake.toString())
    }
}