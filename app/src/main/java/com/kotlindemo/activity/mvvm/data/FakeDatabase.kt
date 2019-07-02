package com.kotlindemo.activity.mvvm.data

/**
 * Created by Manish Patel on 5/22/2019.
 */
// Private primary constructor inaccessible from other classes
class FakeDatabase private constructor() {

    // All the DAOs go here!
    val quoteDao = FakeQuoteDao()

    companion object {
        // @Volatile - Writes to this property are immediately visible to other threads
        @Volatile
        private var instance: FakeDatabase? = null

        // The only way to get hold of the FakeDatabase object
        fun getInstance() =
        // Already instantiated? - return the instance
            // Otherwise instantiate in a thread-safe manner
            instance ?: synchronized(this) {
                // If it's still not instantiated, finally create an object
                // also set the "instance" property to be the currently created one
                instance ?: FakeDatabase().also { instance = it }
            }
    }
}