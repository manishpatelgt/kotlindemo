package com.kotlindemo.basics

import org.junit.Test

/**
 * Created by Manish Patel on 8/10/2019.
 */
//https://blog.mindorks.com/pair-and-triple-in-kotlin

class PairUnitTest {

    @Test
    fun main() {

        //Pair
        //1st way
        val variable1 = "Manish"
        val variable2 = 1 // declaring integer value

        val variableName = Pair(variable1, variable2) // using declared variable in Pair class

        println(variableName.first) // will print the value of variable1
        println(variableName.second) // will print the value of variable2

        //2nd way
        val (firstVariable, secondVariable) = Pair("Hello", 1)
        println(firstVariable)
        println(secondVariable)

        //3rd way
        val (url: String, website: String) = getWebsite()
        println(url)
        println(website)

        //toString()
        val variableName2 = Pair (variable1, variable2) // using declared variable in Pair class
        print(variableName2.toString())

        //toList()
        val list = variableName.toList()
        println(list[0]) // this will print the value of variable1
        println(list[1]) // this will print the value of variable2

        //Triple
        val variable3 = "Patel"

        val variableName3 = Triple (variable1, variable2, variable3) // using declared variable in Triple class

        println(variableName3.first) // will print the value of variable1
        println(variableName3.second) // will print the value of variable2
        println(variableName3.third) // will print the value of variable3
    }

    fun getWebsite(): Pair<String, String> {
        return "www.mindorks.com" to "the Website is"
    }
}