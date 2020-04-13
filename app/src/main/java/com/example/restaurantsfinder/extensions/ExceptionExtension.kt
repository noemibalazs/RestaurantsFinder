package com.example.restaurantsfinder.extensions

import com.example.restaurantsfinder.core.Failure
import com.example.restaurantsfinder.helper.InternetConnectivityException

fun Exception.toFailure(): Failure {
    val obtainExceptionMessage = this.message ?: this.toString()
    fun internetConnectionFailure() = Failure.InternetConnectionFailure(obtainExceptionMessage)
    return when (this) {
        is InternetConnectivityException -> internetConnectionFailure()
        else -> internetConnectionFailure()
    }
}

