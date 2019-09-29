package org.fungorn.audio.di

import io.ktor.client.HttpClient
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.logging.DEFAULT
import io.ktor.client.features.logging.LogLevel
import io.ktor.client.features.logging.Logger
import io.ktor.client.features.logging.Logging
import org.koin.dsl.module

val networkModule = module {
    single { provideHttpClient() }
}

fun provideHttpClient() = HttpClient {
    install(JsonFeature) {
        serializer = GsonSerializer {
            serializeNulls()
        }
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
    }
    expectSuccess = true
}