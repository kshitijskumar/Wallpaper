package com.example.wallpaper.utils

import com.google.gson.GsonBuilder
import java.lang.Exception

fun <T>T.toJson(): String {
    return Injector.gson.toJson(this) ?: ""
}

inline fun <reified T>String.fromJsonToObject(): T? {
    return try {
        Injector.gson.fromJson(this, T::class.java)
    } catch (e: Exception) {
        null
    }
}

fun String.fromPathWithBracesToPathWithoutBraces(): String {
    return this.replace("{", "").replace("}", "")
}