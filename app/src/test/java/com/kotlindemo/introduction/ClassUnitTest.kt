package com.kotlindemo.introduction

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/01_introduction/05_Classes
class ClassUnitTest {

    @Test
    fun main() {

        val customer = Customer()
        println(customer.toString())

        val contact = Contact(1, "mdp3030@gmail.com")

        println(contact.toString())

        println(contact.id)
        println(contact.email)

        contact.email = "manishpatelmd07@gmail.com"
        println(contact.email)

    }

    class Customer

    class Contact(val id: Int, var email: String) {
        override fun toString(): String = "id: $id email: $email"
    }
}