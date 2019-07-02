package com.kotlindemo.specialclasses

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/03_special_classes/03_Sealed%20Classes

class SealedClassUnitTest {

    sealed class ArithmeticOperation {
        class Add(var a: Int, var b: Int) : ArithmeticOperation()
        class Subtract(var a: Int, var b: Int) : ArithmeticOperation()
        class Multiply(var a: Int, var b: Int) : ArithmeticOperation()
        class Divide(var a: Int, var b: Int) : ArithmeticOperation()
    }

    fun execute(op: ArithmeticOperation) = when (op) {
        is ArithmeticOperation.Add -> op.a + op.b
        is ArithmeticOperation.Subtract -> op.a - op.b
        is ArithmeticOperation.Multiply -> op.a * op.b
        is ArithmeticOperation.Divide -> op.a / op.b
    }


    sealed class Mammal(val name: String) {
        class Cat(val catName: String) : Mammal(catName)
        class Human(val humanName: String, val job: String) : Mammal(humanName)
    }

    fun greetMammal(mammal: Mammal): String {
        when (mammal) {                                                                     // 3
            is Mammal.Human -> return "Hello ${mammal.name}; You're working as a ${mammal.job}"    // 4
            is Mammal.Cat -> return "Hello ${mammal.name}"                                         // 5
        }                                                                                   // 6
    }


    @Test
    fun main() {
        //case1
        var result = execute(ArithmeticOperation.Add(2, 3))
        println("Result : $result")

        println("----------------------------------------------")
        println("----------------------------------------------")

        //case2
        println(greetMammal(Mammal.Cat("Snowy")))
        println(greetMammal(Mammal.Human(humanName = "Snowy", job = "Testing")))
    }
}