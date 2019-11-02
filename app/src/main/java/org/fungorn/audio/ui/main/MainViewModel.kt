package org.fungorn.audio.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
    private val _isAuthorsLoading = MutableLiveData<Boolean>().apply { value = false }
    val isAuthorsLoading: LiveData<Boolean> = _isAuthorsLoading

    private val _isAlbumsLoading = MutableLiveData<Boolean>().apply { value = false }
    val isAlbumsLoading: LiveData<Boolean> = _isAlbumsLoading

    private val _isGenresLoading = MutableLiveData<Boolean>().apply { value = false }
    val isGenresLoading: LiveData<Boolean> = _isGenresLoading

    val error = SingleLiveEvent<Throwable>()

    private val _authors = MutableLiveData<List<Author>>().apply { value = listOf() }
    val authors: LiveData<List<Author>> = _authors

    private val _albums = MutableLiveData<List<Album>>().apply { value = listOf() }
    val albums: LiveData<List<Album>> = _albums

    private val _genres = MutableLiveData<List<Genre>>().apply { value = listOf() }
    val genres: LiveData<List<Genre>> = _genres

//    fun getContent() {
//        _isLoading.value = true
//        inBackground({
//            _albums.postValue(withContext(Dispatchers.IO) { albumApi.getAlbums() })
//            _authors.postValue(withContext(Dispatchers.IO) { authorApi.getAuthors() })
//            _genres.postValue(withContext(Dispatchers.IO) { genresApi.getGenres() })
//        },
//            onSuccess = {
//                _isLoading.value = false
//            },
//            onError = {
//                _isLoading.value = false
//                error.value = it
//            }
//        )
//    }

    fun getAuthors() = inBackground({
        _isAuthorsLoading.postValue(true)
        authorApi.getAuthors()
    },
        onSuccess = {
            _isAuthorsLoading.value = false
            _authors.value = it
        },
        onError = {
            _isAuthorsLoading.value = false
            error.value = it
        }
    )

    fun getAlbums() = inBackground({
        _isAlbumsLoading.postValue(true)
        albumApi.getAlbums()
    },
        onSuccess = {
            _isAlbumsLoading.value = false
            _albums.value = it
        },
        onError = {
            _isAlbumsLoading.value = false
            error.value = it
        }
    )

    fun getGenres() = inBackground({
        _isGenresLoading.postValue(true)
        genresApi.getGenres()
    },
        onSuccess = {
            _isGenresLoading.value = false
            _genres.value = it
        },
        onError = {
            _isGenresLoading.value = false
            error.value = it
        }
    )

}