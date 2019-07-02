package com.kotlindemo.introduction

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

//https://play.kotlinlang.org/byExample/01_introduction/06_Generics

class GenericsUnitTest {

    @Test
    fun main() {
        val stack = mutableStackOf(0.62, 3.14, 2.7)
        println(stack)
        println(stack.toString())
    }

    fun <E> mutableStackOf(vararg elements: E) = MutableStack(*elements)

    class MutableStack<E>(vararg items: E) {              // 1

        private val elements = items.toMutableList()

        fun push(element: E) = elements.add(element)        // 2

        fun peek(): E = elements.last()                     // 3

        fun pop(): E = elements.removeAt(elements.size - 1)

        fun isEmpty() = elements.isEmpty()

        fun size() = elements.size

        override fun toString() = "MutableStack(${elements.joinToString()})"
    }


}