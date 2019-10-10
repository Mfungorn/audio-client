package org.fungorn.audio.ui.genre

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.api.GenresApi
import org.fungorn.audio.data.db.repository.AuthorRepository
import org.fungorn.audio.data.db.repository.TrackRepository
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class GenresViewModel(
    private val api: GenresApi
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _authors = MutableLiveData<List<Author>>().apply { value = listOf() }
    val authors: LiveData<List<Author>> = _authors

    private val _tracks = MutableLiveData<List<Track>>().apply { value = listOf() }
    val tracks: LiveData<List<Track>> = _tracks

    private val _albums = MutableLiveData<List<Album>>().apply { value = listOf() }
    val albums: LiveData<List<Album>> = _albums

    fun loadAuthors(genreName: String) {
        _isLoading.value = true
        inBackground({
            _authors.postValue(withContext(Dispatchers.IO) { api.getAuthors(genreName) })
        },
            onSuccess = {
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun loadAlbums(genreName: String) {
        _isLoading.value = true
        inBackground({
            _albums.postValue(withContext(Dispatchers.IO) { api.getAlbums(genreName) })
        },
            onSuccess = {
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun loadTracks(genreName: String) {
        _isLoading.value = true
        inBackground({
            _tracks.postValue(withContext(Dispatchers.IO) { api.getTracks(genreName) })
        },
            onSuccess = {
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }
}