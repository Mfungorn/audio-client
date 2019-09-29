package org.fungorn.audio

import android.app.Application
import android.content.Context
import org.fungorn.audio.di.audioApp
import org.fungorn.audio.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AudioApp : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AudioApp)
            modules(audioApp + networkModule)
        }
    }

    companion object {
        private fun getApp(context: Context): AudioApp {
            return context.applicationContext as AudioApp
        }
    }
}