package com.example.wallpaper.utils

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

fun Context.shortToast(msg: String) {
    this.showToast(msg, Toast.LENGTH_SHORT)
}

fun Context.longToast(msg: String) {
    this.showToast(msg, Toast.LENGTH_LONG)
}

private fun Context.showToast(msg: String, duration: Int) {
    Handler(Looper.getMainLooper()).post {
        Toast.makeText(this, msg, duration).show()
    }
}