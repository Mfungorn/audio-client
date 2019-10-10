package org.fungorn.audio.utils

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

fun <T : Any> ViewModel.inBackground(
    work: suspend (() -> T?),
    onSuccess: ((T?) -> Unit)? = null,
    onError: ((Throwable) -> Unit)? = null
) = viewModelScope.launch {
    try {
        val data = viewModelScope.async(Dispatchers.IO) {
            return@async work()
        }.await()
        onSuccess?.let { it(data) }
    } catch (t: Throwable) {
        onError?.let { it(t) }
    }
}

fun <T : Any> ViewModel.inBackground(
    work: suspend (() -> T?)
) = viewModelScope.launch(Dispatchers.IO) {
    work()
}