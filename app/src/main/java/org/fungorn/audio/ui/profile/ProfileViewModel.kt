package org.fungorn.audio.ui.profile

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.fungorn.audio.data.api.UserApi
import org.fungorn.audio.domain.model.User
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class ProfileViewModel(
    private val api: UserApi,
    private val editor: SharedPreferences.Editor
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    fun getUser() {
        _isLoading.value = true
        inBackground({
            api.getUser()
        },
            onSuccess = {
                _user.value = it
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }
}