package com.example.restaurantsfinder.helper

class OnEvent(id: Int) {

    companion object{
        const val BACK_CLICKED = 0
        const val FAILURE = 1

        fun make(id: Int): OnEvent {
            return OnEvent(id)
        }
    }
}