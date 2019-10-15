package org.fungorn.audio.ui.album

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.api.AlbumApi
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class AlbumViewModel(
    private val api: AlbumApi
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _album = MutableLiveData<Album>()
    val album: LiveData<Album> = _album

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> = _tracks

    fun getContent(albumId: Long) = inBackground(
        {
            _tracks.postValue(withContext(Dispatchers.IO) { api.getAlbumTracks(albumId) })
    },
        onSuccess = { },
        onError = {
            error.value = it
        }
    )

    fun getAlbum(albumId: Long) {
        _isLoading.value = true
        inBackground({
            api.getAlbum(albumId)
        },
            onSuccess = {
                _album.value = it
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun getAlbum(title: String) {
        _isLoading.value = true
        inBackground({
            api.getAlbumByTitle(title)
        },
            onSuccess = {
                _album.value = it
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }
}