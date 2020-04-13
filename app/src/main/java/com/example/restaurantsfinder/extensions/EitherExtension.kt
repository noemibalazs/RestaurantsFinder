package com.example.restaurantsfinder.extensions

import com.example.restaurantsfinder.core.Either

inline fun <T, L, R> Either<L, R>.map(fnR: (R) -> T): Either<L, T> {
    return when (this) {
        is Either.Left -> Either.Left(this.a)
        is Either.Right -> Either.Right(fnR(this.b))
    }
}
