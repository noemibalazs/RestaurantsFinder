package com.example.restaurantsfinder.extensions

import com.example.restaurantsfinder.core.Either
import com.example.restaurantsfinder.core.Failure
import retrofit2.Call
import retrofit2.Response
import java.lang.Exception

fun Call<Void>.executeWithEmptyResponse(): Either<Failure, Any> {
    return try {
        val response: Response<Void> = execute()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                return Either.Right(body)
            } ?: kotlin.run {
                return Either.Right(Any())
            }
        }

        Either.Left(response.toFailure())
    } catch (exp: Exception) {
        Either.Left(exp.toFailure())
    }
}

fun <T : Any> Call<T>.executeAndDeliver(): Either<Failure, T> {
    return try {
        val response: Response<T> = execute()
        if (response.isSuccessful) {
            response.body()?.let { body ->
                return Either.Right(body)
            } ?: kotlin.run {
                return Either.Left(Failure.UnknownFailure("Response successful, body is empty."))
            }
        }
        Either.Left(response.toFailure())
    } catch (exp: Exception) {
        Either.Left(exp.toFailure())
    }
}