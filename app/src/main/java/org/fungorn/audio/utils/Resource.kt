package org.fungorn.audio.utils

sealed class Resource<out T>(
    val data: T? = null,
    val message: String? = null
) {
    class Loading<T>(data: T? = null) : Resource<T>(data)
    class Error<T>(message: String, data: T? = null) : Resource<T>(data, message)
    class Received<T>(data: T) : Resource<T>(data)
}