package com.kotlindemo.utility

import android.widget.Toast
import com.kotlindemo.application.DemoApplication

/**
 * Created by Manish Patel on 5/22/2019.
 */

class ToastManager private constructor() {

    companion object {
        // @Volatile - Writes to this property are immediately visible to other threads
        @Volatile
        private var instance: ToastManager? = null

        // The only way to get hold of the FakeDatabase object
        fun getInstance() =
        // Already instantiated? - return the instance
            // Otherwise instantiate in a thread-safe manner
            instance ?: synchronized(this) {
                // If it's still not instantiated, finally create an object
                // also set the "instance" property to be the currently created one
                instance ?: ToastManager().also { instance = it }
            }
    }

    fun showToast(message: String) {
        Toast.makeText(DemoApplication.context, message, Toast.LENGTH_LONG).show()
    }
}