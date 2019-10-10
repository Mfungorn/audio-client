package org.fungorn.audio.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.api.AuthorApi
import org.fungorn.audio.data.api.GenresApi
import org.fungorn.audio.data.api.TrackApi
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Genre
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class SearchViewModel(
    private val authorApi: AuthorApi,
    private val trackApi: TrackApi,
    private val genresApi: GenresApi
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _authors = MutableLiveData<List<Author>>().apply { value = listOf() }
    val authors: LiveData<List<Author>> = _authors

    private val _tracks = MutableLiveData<List<Track>>().apply { value = listOf() }
    val tracks: LiveData<List<Track>> = _tracks

    private val _genres = MutableLiveData<List<Genre>>().apply { value = listOf() }
    val genres: LiveData<List<Genre>> = _genres

    fun globalSearch(query: String) {
        _isLoading.value = true
        inBackground({
            _authors.postValue(withContext(Dispatchers.IO) {
                authorApi.getAuthorsWithNameStartsWith(
                    query
                )
            })
            _tracks.postValue(withContext(Dispatchers.IO) {
                trackApi.getTracksWithTitleStartsWith(
                    query
                )
            })
            _genres.postValue(withContext(Dispatchers.IO) {
                genresApi.getGenresWithNameStartsWith(
                    query
                )
            })
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

    fun resetSearch() {
        _authors.value = emptyList()
        _tracks.value = emptyList()
        _genres.value = emptyList()
    }
}