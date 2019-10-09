package org.fungorn.audio.utils

import kotlinx.coroutines.*

object Coroutines {
    fun <T : Any> io(work: suspend (() -> T?)): Job =
        CoroutineScope(Dispatchers.IO).launch {
            work()
        }

    fun <T : Any> ioThenMain(
        work: suspend (() -> T?),
        onSuccess: ((T?) -> Unit)? = null,
        onError: ((Throwable) -> Unit)? = null
    ): Job = CoroutineScope(Dispatchers.Main).launch {
        try {
            val data = CoroutineScope(Dispatchers.IO).async {
                return@async work()
            }.await()
            onSuccess?.let { it(data) }
        } catch (t: Throwable) {
            onError?.let { it(t) }
        }
    }
}