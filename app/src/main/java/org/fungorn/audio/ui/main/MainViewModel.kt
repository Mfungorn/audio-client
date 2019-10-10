package org.fungorn.audio.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.api.AlbumApi
import org.fungorn.audio.data.api.AuthorApi
import org.fungorn.audio.data.api.GenresApi
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Genre
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class MainViewModel(
    private val authorApi: AuthorApi,
    private val albumApi: AlbumApi,
    private val genresApi: GenresApi
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _authors = MutableLiveData<List<Author>>().apply { value = listOf() }
    val authors: LiveData<List<Author>> = _authors

    private val _albums = MutableLiveData<List<Album>>().apply { value = listOf() }
    val albums: LiveData<List<Album>> = _albums

    private val _genres = MutableLiveData<List<Genre>>().apply { value = listOf() }
    val genres: LiveData<List<Genre>> = _genres

    fun getContent() {
        _isLoading.value = true
        inBackground({
            _authors.postValue(withContext(Dispatchers.IO) { authorApi.getAuthors() })
            _albums.postValue(withContext(Dispatchers.IO) { albumApi.getAlbums() })
            _genres.postValue(withContext(Dispatchers.IO) { genresApi.getGenres() })
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

    fun content() {
        getAuthors()
        getAlbums()
        getGenres()
    }

    fun getAuthors() = inBackground({
        authorApi.getAuthors()
    },
        onSuccess = {
            _authors.value = it
        },
        onError = {
            error.value = it
        }
    )

    fun getAlbums() = inBackground({
        albumApi.getAlbums()
    },
        onSuccess = {
            _albums.value = it
        },
        onError = {
            error.value = it
        }
    )

    fun getGenres() = inBackground({
        genresApi.getGenres()
    },
        onSuccess = {
            _genres.value = it
        },
        onError = {
            error.value = it
        }
    )

}