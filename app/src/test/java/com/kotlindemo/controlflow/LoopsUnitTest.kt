package com.kotlindemo.controlflow

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/02_control_flow/02_Loops
class LoopsUnitTest {

    fun eatACake(index: Int) = println("Eat a Cake ${index}")
    fun bakeACake(index: Int) = println("Bake a Cake ${index}")

    @Test
    fun main() {

        //1st case
        val cakes = listOf("carrot", "cheese", "chocolate")

        for (cake in cakes) {                               // 1
            println("Yummy, it's a $cake cake!")
        }

        println("----------------------------------------------")
        println("----------------------------------------------")

        //2nd case
        var cakesEaten = 0
        var cakesBaked = 0

        while (cakesEaten < 5) {                    // 1
            eatACake(cakesEaten)
            cakesEaten++
        }

        do {                                        // 2
            bakeACake(cakesBaked)
            cakesBaked++
        } while (cakesBaked < cakesEaten)


        println("----------------------------------------------")
        println("----------------------------------------------")

        //3rd case
        val animals = listOf(Animal("zebra"), Animal("lion"), Animal("tiger"))
        val zoo = Zoo(animals)

        for (animal in zoo) {                                   // 3
            println("Watch out, it's a ${animal.name}")
        }
    }

    class Animal(val name: String)

    class Zoo(val animals: List<Animal>) {

        operator fun iterator(): Iterator<Animal> {             // 1
            return animals.iterator()                           // 2
        }
    }
}