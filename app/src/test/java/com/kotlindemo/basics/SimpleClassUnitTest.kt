package com.kotlindemo.basics

import org.junit.Test

/**
 * Created by Manish Patel on 7/11/2019.
 */
//https://www.callicoder.com/kotlin-classes-objects-constructors-initializers/

class DataClassUnitTest {

    class Person constructor(var firstName: String, var lastName: String) {

        var age: Int? = null

        /*var firstName: String
        var lastName: String*/

        // Secondary Constructor
        constructor(firstName: String, lastName: String, age: Int) : this(firstName, lastName) {
            this.age = if (age > 0) age else throw IllegalArgumentException("Age must be greater than zero")
        }

        init {
            //firstName = _firstName
            //lastName = _lastName

            println("Initialized a new Person object with firstName = $firstName and lastName = $lastName")

        }

        /*override fun toString(): String {
            return "firstName: $firstName : lastName : $lastName"
        }*/
    }

    @Test
    fun main() {

        // Calls the primary constructor (Age will be null in this case)
        val person = Person("Manish", "Patel")
        println("firstName ${person.firstName} : lastName ${person.lastName}")

        // Calls the secondary constructor
        val person2 = Person("Manish", "Patel", 28)
        println("firstName ${person2.firstName} : lastName ${person2.lastName} : age ${person2.age}")

    }
}