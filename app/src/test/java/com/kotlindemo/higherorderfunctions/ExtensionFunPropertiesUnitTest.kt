package com.kotlindemo.higherorderfunctions

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */

//https://play.kotlinlang.org/byExample/04_functional/03_extensionFunctions
class ExtensionFunPropertiesUnitTest {

    data class Item(val name: String, val price: Float)                                   // 1

    data class Order(val items: Collection<Item>)

    fun Order.maxPricedItemValue(): Float = this.items.maxBy { it.price }?.price ?: 0F    // 2
    fun Order.maxPricedItemName() = this.items.maxBy { it.price }?.name ?: "NO_PRODUCTS"

    val Order.commaDelimitedItemNames: String                                             // 3
        get() = items.map { it.name }.joinToString()

    @Test
    fun main() {

        val order = Order(listOf(Item("Bread", 25.0F), Item("Wine", 38.0F), Item("Water", 12.0F)))

        println("Max priced item name: ${order.maxPricedItemName()}")                     // 4
        println("Max priced item value: ${order.maxPricedItemValue()}")
        println("Items: ${order.commaDelimitedItemNames}")

    }
}