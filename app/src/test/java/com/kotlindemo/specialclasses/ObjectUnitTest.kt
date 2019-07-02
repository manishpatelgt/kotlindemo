package com.kotlindemo.specialclasses

import org.junit.Test
import java.util.*

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/03_special_classes/04_Object

class ObjectUnitTest {

    class LuckDispatcher {                    //1
        fun getNumber() {                     //2
            var objRandom = Random()
            println(objRandom.nextInt(90))
        }
    }

    fun rentPrice(standardDays: Int, festivityDays: Int, specialDays: Int): Unit {  //1

        val dayRates = object {                                                     //2
            var standard: Int = 30 * standardDays
            var festivity: Int = 50 * festivityDays
            var special: Int = 100 * specialDays
        }

        val total = dayRates.standard + dayRates.festivity + dayRates.special       //3
        print("Total price: $$total")                                               //4
    }

    object DoAuth {                                                 //1
        fun takeParams(username: String, password: String) {         //2
            println("input Auth parameters = $username:$password")
        }
    }

    class BigBen {                                  //1
        companion object Bonger {                   //2
            fun getBongs(nTimes: Int) {             //3
                for (i in 1..nTimes) {
                    print("BONG ")
                }
            }
        }
    }

    @Test
    fun main() {

        //case1: by classes and Object
        val d1 = LuckDispatcher()             //3
        val d2 = LuckDispatcher()

        d1.getNumber()                        //4
        d2.getNumber()

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case2: object Expression
        rentPrice(10, 2, 1)

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case3: object Declaration
        DoAuth.takeParams("foo", "qwerty")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case4:Companion Objects
        BigBen.getBongs(5)
    }
}