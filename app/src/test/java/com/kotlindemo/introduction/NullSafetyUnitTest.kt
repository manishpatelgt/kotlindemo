package com.kotlindemo.introduction

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

//https://play.kotlinlang.org/byExample/01_introduction/04_Null%20Safety
class NullSafetyUnitTest {

    @Test
    fun main() {

        var neverNull: String = "This can't be null"            // 1

        //neverNull = null                                        // 2

        var nullable: String? = "You can keep a null here"      // 3

        nullable = null                                         // 4

        var inferredNonNull = "The compiler assumes non-null"   // 5

        //inferredNonNull = null                                  // 6

        fun strLength(notNull: String): Int {                   // 7
            return notNull.length
        }

        fun describeString(maybeString: String?): String {              // 1
            return if (maybeString != null && maybeString.isNotEmpty()) {        // 2
                "String of length ${maybeString.length}"
            } else {
                "Empty or null string"                           // 3
            }
        }


        println(strLength(neverNull))
        println(strLength(inferredNonNull))   // 8
        //strLength(nullable)

        println(describeString(neverNull))
        println(describeString(inferredNonNull))
        println(describeString(nullable))
    }
}