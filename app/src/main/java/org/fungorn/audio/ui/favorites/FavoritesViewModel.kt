package org.fungorn.audio.ui.favorites

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.fungorn.audio.data.db.repository.AuthorRepository
import org.fungorn.audio.data.db.repository.TrackRepository
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.utils.SingleLiveEvent
import org.fungorn.audio.utils.inBackground

class FavoritesViewModel(
    private val trackRepository: TrackRepository,
    private val authorRepository: AuthorRepository
) : ViewModel() {
    private val _isLoading = MutableLiveData<Boolean>().apply { value = false }
    val isLoading: LiveData<Boolean> = _isLoading

    val error = SingleLiveEvent<Throwable>()

    private val _authors = MutableLiveData<List<Author>>().apply { value = listOf() }
    val authors: LiveData<List<Author>> = _authors

    private val _tracks = MutableLiveData<List<Track>>().apply { value = listOf() }
    val tracks: LiveData<List<Track>> = _tracks

    fun loadFavorites() {
        _isLoading.value = true
        inBackground({
            _authors.postValue(withContext(Dispatchers.IO) { authorRepository.getAll() })
            _tracks.postValue(withContext(Dispatchers.IO) { trackRepository.getAll() })
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

    fun loadTracks() {
        _isLoading.value = true
        inBackground({
            _tracks.postValue(withContext(Dispatchers.IO) { trackRepository.getAll() })
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