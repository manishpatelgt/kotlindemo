package com.kotlindemo.delegation

import org.junit.Test

/**
 * Created by Manish Patel on 5/6/2019.
 */
//https://play.kotlinlang.org/byExample/07_Delegation/01_delegationPattern

class DelegationPatternUnitTest {

    interface SoundBehavior {                                                          // 1
        fun makeSound()
    }

    class ScreamBehavior(val n: String) : SoundBehavior {                                // 2
        override fun makeSound() = println("${n.toUpperCase()} !!!")
    }

    class RockAndRollBehavior(val n: String) : SoundBehavior {                           // 2
        override fun makeSound() = println("I'm The King of Rock 'N' Roll: $n")
    }

    // Tom Araya is the "singer" of Slayer
    class TomAraya(n: String) : SoundBehavior by ScreamBehavior(n)                       // 3

    // You should know ;)
    class ElvisPresley(n: String) : SoundBehavior by RockAndRollBehavior(n)              // 3


    @Test
    fun main() {
        val tomAraya = TomAraya("Trash Metal")
        tomAraya.makeSound()                                                            // 4
        val elvisPresley = ElvisPresley("Dancin' to the Jailhouse Rock.")
        elvisPresley.makeSound()
    }
}