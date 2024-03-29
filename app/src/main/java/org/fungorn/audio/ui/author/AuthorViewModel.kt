package org.fungorn.audio.ui.author

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.api.AuthorApi
import org.fungorn.audio.data.db.repository.AuthorRepository
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class AuthorViewModel(
    private val api: AuthorApi,
    private val repository: AuthorRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    private val _isAlbumsLoading = MutableLiveData<Boolean>().apply { value = false }
    val isAlbumsLoading: LiveData<Boolean> = _isAlbumsLoading

    private val _isTracksLoading = MutableLiveData<Boolean>().apply { value = false }
    val isTracksLoading: LiveData<Boolean> = _isTracksLoading

    val error = SingleLiveEvent<Throwable>()

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author> = _author

    private val _tracks = MutableLiveData<List<Track>>()
    val tracks: LiveData<List<Track>> = _tracks

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    val isFavorite = SingleLiveEvent<Boolean>().apply { value = false }

    fun getContent(authorId: Long) {
        getAuthorAlbums(authorId)
        getAuthorTracks(authorId)
    }

    fun getAuthorTracks(authorId: Long) = inBackground({
        _isTracksLoading.postValue(true)
        _tracks.postValue(withContext(Dispatchers.IO) { api.getAuthorTracks(authorId) })
    },
        onSuccess = {
            _isTracksLoading.value = false
        },
        onError = {
            _isTracksLoading.value = false
            error.value = it
        }
    )

    fun getAuthorAlbums(authorId: Long) = inBackground(
        {
            _isAlbumsLoading.postValue(true)
            _albums.postValue(withContext(Dispatchers.IO) { api.getAuthorAlbums(authorId) })
    },
        onSuccess = {
            _isAlbumsLoading.value = false
        },
        onError = {
            _isAlbumsLoading.value = false
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
                _author.value = it.also { getContent(it!!.id) }
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