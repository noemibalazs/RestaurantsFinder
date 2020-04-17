package com.example.restaurantsfinder.helper

class OnClickEvent(id: Int) {

    companion object{
        const val BACK_CLICKED = 0

        fun make(id: Int): OnClickEvent {
            return OnClickEvent(id)
        }
    }
}