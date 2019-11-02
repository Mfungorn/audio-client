package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.Track

class AuthorApi(
    private val client: HttpClient
) {
    suspend fun getAuthor(authorId: Long) = client.get<Author>(
        "${BuildConfig.API_URL}/authors/$authorId"
    )

    suspend fun getAuthorAlbums(authorId: Long) = client.get<List<Album>>(
        "${BuildConfig.API_URL}/authors/$authorId/albums"
    )

    suspend fun getAuthorTracks(authorId: Long) = client.get<List<Track>>(
        "${BuildConfig.API_URL}/authors/$authorId/compositions"
    )

    suspend fun getAuthorByName(name: String) = client.get<Author>(
        "${BuildConfig.API_URL}/authors"
    ) {
        parameter("name", name)
    }

    suspend fun getAuthors(): List<Author> = client.get(
        "${BuildConfig.API_URL}/authors/all"
    )

    suspend fun getAuthorsWithNameStartsWith(query: String): List<Author> = client.get(
        "${BuildConfig.API_URL}/authors/search"
    ) {
        parameter("q", query)
    }
}