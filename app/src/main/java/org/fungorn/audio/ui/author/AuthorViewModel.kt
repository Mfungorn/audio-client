package org.fungorn.audio.ui.author

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.api.AlbumApi
import org.fungorn.audio.data.api.AuthorApi
import org.fungorn.audio.data.api.TrackApi
import org.fungorn.audio.data.db.repository.AuthorRepository
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class AuthorViewModel(
    private val api: AuthorApi,
    private val albumApi: AlbumApi,
    private val trackApi: TrackApi,
    private val repository: AuthorRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author> = _author

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> = _tracks

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    val isFavorite = SingleLiveEvent<Boolean>().apply { value = false }

    fun getContent() = inBackground({
        _albums.postValue(withContext(Dispatchers.IO) { albumApi.getAlbums() })
        _tracks.postValue(withContext(Dispatchers.IO) { trackApi.getTracks() })
    },
        onSuccess = { },
        onError = {
            error.value = it
        }
    )

    fun getAuthor(authorId: Long) {
        _isLoading.value = true
        inBackground({
            api.getAuthor(authorId)
        },
            onSuccess = {
                _author.value = it
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun getAuthor(name: String) {
        _isLoading.value = true
        inBackground({
            api.getAuthorByName(name)
        },
            onSuccess = {
                _author.value = it
                _isLoading.value = false
            },
            onError = {
                _isLoading.value = false
                error.value = it
            }
        )
    }

    fun checkIsFavorite(authorId: Long) = inBackground({
        repository.existsById(authorId)
    },
        onSuccess = { isFavorite.value = it },
        onError = { error.value = it }
    )

    fun checkIsFavorite(name: String) = inBackground({
        repository.existsByName(name)
    },
        onSuccess = { isFavorite.value = it },
        onError = { error.value = it }
    )
}