package com.example.restaurantsfinder.core

sealed class Failure(val message: String) {

    class UnknownFailure(message: String) : Failure(message)
    class ResourceNotFoundFailure(message: String) : Failure(message)
    class NetworkConnectingFailure(message: String) : Failure(message)
    class DataBaseFailure(message: String) : Failure(message)
    class InternetConnectionFailure(message: String) : Failure(message)

    override fun toString(): String {
        return "Failure(message='$message')"
    }
}