package com.kotlindemo.activity.otherthings.shimmer.utils

import java.io.IOException
import com.kotlindemo.activity.otherthings.shimmer.models.Result

/**
 * Created by Manish Patel on 7/1/2019.
 */

suspend fun <T : Any> safeApiCall(call: suspend () -> Result<T>, errorMessage: String): Result<T> = try {
    call.invoke()
} catch (e: Exception) {
    Result.Error(IOException(errorMessage, e))
}

val <T> T.exhaustive: T get() = this