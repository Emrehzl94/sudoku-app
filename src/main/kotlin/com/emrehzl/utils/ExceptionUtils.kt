package com.emrehzl.utils

object ExceptionUtils {
    fun getExceptionNameString(e: Exception): String {
        return e.toString().split(":")[0]
    }
}