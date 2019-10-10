package org.fungorn.audio.ui.track

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.fungorn.audio.data.api.TrackApi
import org.fungorn.audio.data.db.repository.TrackRepository
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class TrackViewModel(
    private val api: TrackApi,
    private val repository: TrackRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _track = MutableLiveData<Track>()
    val track: LiveData<Track> = _track

    val isFavorite = SingleLiveEvent<Boolean>().apply { value = false }

    fun getTrack(trackId: Long) {
        _isLoading.value = true
        inBackground({
            api.getTrack(trackId)
        },
            onSuccess = {
                _track.value = it
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun checkIsFavorite(trackId: Long) = inBackground({
        repository.existsById(trackId)
    },
        onSuccess = { isFavorite.value = it },
        onError = { error.value = it }
    )
}