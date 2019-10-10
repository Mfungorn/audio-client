package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import org.fungorn.audio.domain.model.Genre

class GenresApi(
    private val client: HttpClient
) {
    suspend fun getGenre(name: String) = null

    suspend fun getGenreAuthors(name: String) = null
    suspend fun getGenreAlbums(name: String) = null
    suspend fun getGenreTracks(name: String) = null

    suspend fun getGenres(): List<Genre> = listOf()

    suspend fun getGenresWithNameStartsWith(query: String): List<Genre> = listOf()
}