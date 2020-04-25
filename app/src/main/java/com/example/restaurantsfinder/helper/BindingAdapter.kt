package com.example.restaurantsfinder.helper

import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.databinding.BindingAdapter

class BindingAdapter {

    @BindingAdapter("showIf")
    fun showIf(view: AppCompatEditText, show: Boolean) {
        view.visibility = if (show) View.VISIBLE else View.GONE
    }
}