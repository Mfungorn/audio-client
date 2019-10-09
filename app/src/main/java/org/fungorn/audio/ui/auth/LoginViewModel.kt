package org.fungorn.audio.ui.auth

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.fungorn.audio.data.api.FavoritesApi
import org.fungorn.audio.data.api.LoginApi
import org.fungorn.audio.data.db.repository.AuthorRepository
import org.fungorn.audio.data.db.repository.TrackRepository
import org.fungorn.audio.domain.model.LoginRequest
import org.fungorn.audio.utils.Coroutines
import org.fungorn.audio.utils.SingleLiveEvent

class LoginViewModel(
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val api: LoginApi,
    private val favoritesApi: FavoritesApi,
    private val authorRepository: AuthorRepository,
    private val trackRepository: TrackRepository
) : ViewModel() {
    val error = SingleLiveEvent<String>().apply { value = null }
    val signedInEvent = SingleLiveEvent<Unit>()

    fun signIn(login: String, password: String) {
        val request = LoginRequest(login, password)
        Coroutines.ioThenMain({
            val token = api.signIn(request)
            sharedPreferencesEditor.putString("token", token)
            favoritesApi.loadFavorites()
        },
            onSuccess = {
                signedInEvent.call() // navigation
                it?.let { favorites ->
                    viewModelScope.launch(Dispatchers.IO) {
                        favorites.authors.forEach { author -> authorRepository.add(author) }
                        favorites.tracks.forEach { track -> trackRepository.add(track) }
                    }
                }
            },
            onError = { error.value = it.message }
        )
    }
}