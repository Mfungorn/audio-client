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
import org.fungorn.audio.domain.model.SignUpRequest
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class LoginViewModel(
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val api: LoginApi,
    private val favoritesApi: FavoritesApi,
    private val authorRepository: AuthorRepository,
    private val trackRepository: TrackRepository
) : ViewModel() {
    val error = SingleLiveEvent<String>().apply { value = null }
    val signedInEvent = SingleLiveEvent<Unit>()
    val signedUpEvent = SingleLiveEvent<Unit>()

    val signUpRequest = SignUpRequest(
        name = "",
        email = "",
        password = ""
    )

    fun signUp() {
        inBackground({
            api.signUp(signUpRequest)
        },
            onSuccess = {
                signedUpEvent.call()
            },
            onError = {
                error.value = it.message
            }
        )
    }

    fun signIn(login: String, password: String) {
        val request = LoginRequest(login, password)
        inBackground(
            {
            val token = api.signIn(request)
            sharedPreferencesEditor.putString("token", token.substringAfter("Bearer "))
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