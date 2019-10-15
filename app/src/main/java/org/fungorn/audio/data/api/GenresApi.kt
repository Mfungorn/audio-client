package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Genre
import org.fungorn.audio.domain.model.Track

class GenresApi(
    private val client: HttpClient
) {
    suspend fun getGenre(name: String) = client.get<Genre>(
        "${BuildConfig.API_URL}/genres/$name"
    )

    suspend fun getGenres(): List<Genre> = client.get("${BuildConfig.API_URL}/genres")
    suspend fun getGenreAuthors(name: String) = client.get<List<Author>>(
        "${BuildConfig.API_URL}/genres/$name/authors"
    )

    suspend fun getGenreAlbums(name: String) = client.get<List<Album>>(
        "${BuildConfig.API_URL}/genres/$name/albums"
    )

    suspend fun getGenreTracks(name: String) = client.get<List<Track>>(
        "${BuildConfig.API_URL}/genres/$name/compositions"
    )

    suspend fun getGenresWithNameStartsWith(query: String): List<Genre> = client.get(
        "${BuildConfig.API_URL}/genres/search   "
    ) {
        parameter("q", query)
    }
}