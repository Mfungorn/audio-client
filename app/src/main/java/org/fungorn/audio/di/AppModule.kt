package org.fungorn.audio.di

import android.app.Application
import android.content.SharedPreferences
import androidx.room.Room
import org.fungorn.audio.data.db.AudioDatabase
import org.fungorn.audio.data.db.repository.AuthorRepository
import org.fungorn.audio.data.db.repository.TrackRepository
import org.fungorn.audio.ui.auth.LoginViewModel
import org.fungorn.audio.ui.favorites.FavoritesViewModel
import org.fungorn.audio.ui.main.MainViewModel
import org.fungorn.audio.ui.profile.ProfileViewModel
import org.fungorn.audio.ui.search.SearchViewModel
import org.fungorn.audio.ui.track.TrackViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

private var appModule = module {
    viewModel { FavoritesViewModel(get(), get()) }
    viewModel { MainViewModel(get(), get(), get()) }
    viewModel { SearchViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get(), get()) }
    viewModel { TrackViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get(), get(), get(), get()) }

    single {
        getSharedPrefs(androidApplication())
    }

    single<SharedPreferences.Editor> {
        getSharedPrefs(androidApplication()).edit()
    }

    single {
        Room.databaseBuilder(
            androidApplication(),
            AudioDatabase::class.java,
            AudioDatabase.NAME
        ).build()
    }

    single { get<AudioDatabase>().authorDao() }
    single { get<AudioDatabase>().trackDao() }

    single { TrackRepository(get()) }
    single { AuthorRepository(get()) }
}

fun getSharedPrefs(androidApplication: Application): SharedPreferences {
    return androidApplication.getSharedPreferences("default", android.content.Context.MODE_PRIVATE)
}

val audioApp = listOf(appModule)