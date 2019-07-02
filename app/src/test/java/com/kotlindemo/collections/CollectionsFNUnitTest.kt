package com.kotlindemo.collections

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/05_Collections/05_existential
//https://play.kotlinlang.org/byExample/05_Collections/06_find
//https://play.kotlinlang.org/byExample/05_Collections/07_firstlast
//https://play.kotlinlang.org/byExample/05_Collections/08_count
//https://play.kotlinlang.org/byExample/05_Collections/10_associateBy
//https://play.kotlinlang.org/byExample/05_Collections/11_partition
//https://play.kotlinlang.org/byExample/05_Collections/12_flatMap
//https://play.kotlinlang.org/byExample/05_Collections/13_max
//https://play.kotlinlang.org/byExample/05_Collections/14_sorted
//https://play.kotlinlang.org/byExample/05_Collections/15_Map_getValue
//https://play.kotlinlang.org/byExample/05_Collections/16_zip
//https://play.kotlinlang.org/byExample/05_Collections/17_getOrElse

class CollectionsFNUnitTest() {

    data class Person(val name: String, val city: String, val phone: String) // 1

    @Test
    fun main() {

        val numbers = listOf(1, -2, 3, -4, 5, -6)            // 1

        //case1: Function any
        val anyNegative = numbers.any { it < 0 }             // 2
        val anyGT6 = numbers.any { it > 6 }                  // 3

        println("Numbers $numbers")
        println("Is there any number less than 0: $anyNegative")
        println("Is there any number greater than 0: $anyGT6")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case2: Function all
        val allEven = numbers.all { it % 2 == 0 }            // 2
        val allLess6 = numbers.all { it < 6 }
        println("All numbers are even: $allEven")
        println("All numbers are less than 6: $allLess6")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case3: Function none
        val allEven2 = numbers.none { it % 2 == 1 }           // 2
        val allLess62 = numbers.none { it > 6 }               // 3
        println("All numbers are even: $allEven2")
        println("No element greater than 6: $allLess62")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case4: find, findLast check
        val words = listOf("Lets", "find", "something", "in", "collection", "somehow")  // 1
        val first = words.find { it.startsWith("some") }                                // 2
        val last = words.findLast { it.startsWith("some") }                             // 3
        val nothing = words.find { it.contains("nothing") }

        println("The first word starting with 'some' is $first")
        println("The first word starting with 'some' is $last")
        println("The first word starting with 'nothing' is $nothing")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case5: first(), last()
        val firstelement = numbers.first()                          // 2
        val lastelement = numbers.last()                            // 3

        val firstEven = numbers.first { it % 2 == 0 }        // 4
        val lastOdd = numbers.last { it % 2 != 0 }

        println("First Element $firstelement")
        println("Last Element $lastelement")

        println("First Even $firstEven")
        println("Last Odd $lastOdd")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case6: firstOrNull, lastOrNull
        val words2 = listOf("foo", "bar", "baz", "faz")         // 1
        val empty = emptyList<String>()                        // 2

        val firstElement = empty.firstOrNull()                        // 3
        val lastElement = empty.lastOrNull()                          // 4

        val firstF = words2.firstOrNull { it.startsWith('f') }  // 5
        val firstZ = words2.firstOrNull { it.startsWith('z') }  // 6
        val lastF = words2.lastOrNull { it.endsWith('f') }      // 7
        val lastZ = words2.lastOrNull { it.endsWith('z') }      // 8

        println("First $firstElement")
        println("Last $lastElement")

        println("First starts with 'f' $firstF, last starts with 'z' is $firstZ")
        println("First ends with 'f' $lastF, last ends with 'z' is $lastZ")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case6: count
        val totalCount = numbers.count()                     // 2
        val evenCount = numbers.count { it % 2 == 0 }        // 3

        println("Total number of elements $totalCount")
        println("Number of even elements $evenCount")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case7:associateBy, groupBy
        val people = listOf(                                                     // 2
            Person("John", "Boston", "+1-888-123456"),
            Person("Sarah", "Munich", "+49-777-789123"),
            Person("Svyatoslav", "Saint-Petersburg", "+7-999-456789"),
            Person("Vasilisa", "Saint-Petersburg", "+7-999-123456")
        )

        val phoneBook = people.associateBy { it.phone }                          // 3
        val cityBook = people.associateBy(Person::phone, Person::city)           // 4
        val peopleCities = people.groupBy(Person::city, Person::name)

        println("People: $people")
        println("Phone book: $phoneBook")
        println("City book: $cityBook")
        println("People living in each city: $peopleCities")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case8: partition
        val (even, odd) = numbers.partition { it % 2 == 0 }           // 2
        val (positives, negatives) = numbers.partition { it > 0 } // 3

        println("Numbers $numbers")

        println("Even numbers $even")
        println("Odd numbers $odd")

        println("Positive numbers $positives")
        println("Negative numbers $negatives")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case9: flatMap
        val numbers2 = listOf(1, 2, 3)                        // 1
        val tripled = numbers2.flatMap { listOf(it, it, it) } // 2
        val doubled = numbers2.flatMap { listOf(it, it) } // 2

        println("Numbers $numbers2")
        println("Tripled $tripled")
        println("Doubled $doubled")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case10: min, max
        //val empty = emptyList<Int>()
        println("Numbers: $numbers2, min = ${numbers2.min()} max = ${numbers2.max()}") // 1
        println("Empty: $empty, min = ${empty.min()}, max = ${empty.max()}")        // 2

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case11: sorted
        val shuffled = listOf(5, 4, 2, 1, 3)     // 1
        val natural = shuffled.sorted()          // 2
        val inverted = shuffled.sortedBy { -it } // 3

        println("Shuffled $shuffled")
        println("Natural order $natural")
        println("Inverted natural order $inverted")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case12: map element access
        val map = mapOf("key" to 42)

        val value1 = map["key"]                                     // 1
        val value2 = map["key2"]                                    // 2

        val value3: Int = map.getValue("key")                       // 1

        val mapWithDefault = map.withDefault { k -> k.length }
        val value4 = mapWithDefault.getValue("key2")                // 3

        try {
            map.getValue("anotherKey")                              // 4
        } catch (e: NoSuchElementException) {
            println("Message: $e")
        }

        println("Value1 $value1")
        println("Value2 $value2")
        println("Value3 $value3")
        println("Value4 $value4")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case13: zip
        val A = listOf("a", "b", "c")                  // 1
        val B = listOf(1, 2, 3, 4)                     // 1

        val resultPairs = A zip B                      // 2
        val resultReduce = A.zip(B) { a, b -> "$a$b" } // 3

        println("A to B:  $resultPairs")
        println("AB: $resultReduce")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case14: getOrElse
        val list = listOf(0, 10, 20)
        println(list.getOrElse(1) { 42 })    // 1
        println(list.getOrElse(10) { 42 })   // 2
        println(list.getOrElse(100) { 4100 })   // 2

        //case15: getOrElse in map
        val map2 = mutableMapOf<String, Int?>()
        println(map2.getOrElse("x") { 1 })       // 1

        map2["x"] = 3
        println(map2.getOrElse("x") { 1 })       // 2

        map2["x"] = null
        println(map2.getOrElse("x") { 1 })
    }
}