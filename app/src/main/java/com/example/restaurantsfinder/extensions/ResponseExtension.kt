package com.example.restaurantsfinder.extensions

import com.example.restaurantsfinder.core.Failure
import retrofit2.Response

fun <T : Any> Response<T>.toFailure(): Failure {
    return errorBody()?.let {
        Failure.NetworkConnectingFailure("Network connecting failure: ${code()}")
    } ?: kotlin.run {
        Failure.UnknownFailure("Unknown failure")
    }
}