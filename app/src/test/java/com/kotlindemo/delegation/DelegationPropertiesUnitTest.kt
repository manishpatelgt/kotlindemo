package com.kotlindemo.delegation

import org.junit.Test
import kotlin.properties.Delegates

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/07_Delegation/02_DelegatedProperties
//https://en.proft.me/2018/04/18/working-delegated-properties-kotlin/

/**
 *
What’s the difference between lazy and lateinit?
Both are used to delay the property initializations in Kotlin:
lateinit:  is a modifier used with var and is used to set the value to the var at a later point.
lazy: is a method or rather say lambda expression. It’s set on a val only. The val would be created at runtime when it’s required.
val x: Int by lazy { 10 }
lateinit var y: String
 **/

class DelegationPropertiesUnitTest {

    val lazyData: Double by lazy {
        println("Initializing lazyData")
        2.0
    }

    var observableData: String by Delegates.observable("Initial value") { property, oldValue, newValue ->
        println("${property.name}: $oldValue -> $newValue")
    }

    class User {
        var name: String by Delegates.observable("<no name>") { prop, old, new ->
            println("$old -> $new")
        }
    }

    class Travel {
        var placeName: String by Delegates.vetoable("<>") { property, oldValue, newValue ->
            if (!newValue.equals("Paris")) {
                return@vetoable false
            }
            true
        }
    }

    @Test
    fun main() {
        //Lazy
        println(lazyData)
        println(lazyData)

        //observable
        observableData = "New value"
        observableData = "Another value"

        //observable
        val user = User()
        user.name = "first"
        user.name = "second"

        //vetoable
        val paris = Travel()
        paris.placeName = "Paris"
        paris.placeName = "Italy"
        println(paris.placeName)
    }
}