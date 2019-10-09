package org.fungorn.audio.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.features.json.GsonSerializer
import io.ktor.client.features.json.JsonFeature
import okhttp3.logging.HttpLoggingInterceptor
import org.fungorn.audio.data.api.*
import org.fungorn.audio.network.TokenInterceptor
import org.koin.dsl.module

val networkModule = module {
    factory { TokenInterceptor(get()) }

    single { getHttpClient(get()) }
    single { AuthorApi(get()) }
    single { AlbumApi(get()) }
    single { TrackApi(get()) }
    single { UserApi(get()) }
    single { FavoritesApi(get()) }
    single { LoginApi(getLoginHttpClient()) }
}

fun getHttpClient(tokenInterceptor: TokenInterceptor) = HttpClient(OkHttp) {
    engine {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addInterceptor(tokenInterceptor)
        addNetworkInterceptor(loggingInterceptor)
    }
    install(JsonFeature) {
        serializer = GsonSerializer {
            serializeNulls()
        }
    }
    expectSuccess = true
}

fun getLoginHttpClient() = HttpClient(OkHttp) {
    engine {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        addNetworkInterceptor(loggingInterceptor)
    }
    install(JsonFeature) {
        serializer = GsonSerializer {
            serializeNulls()
        }
    }
    expectSuccess = true
}