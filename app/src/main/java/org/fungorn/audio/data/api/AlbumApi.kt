package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.Album
import org.fungorn.audio.domain.model.Track

class AlbumApi(
    private val client: HttpClient
) {
    suspend fun getAlbum(albumId: Long) =
        client.get<Album>("${BuildConfig.API_URL}/albums/$albumId")

    suspend fun getAlbumTracks(albumId: Long) = client.get<List<Track>>(
        "${BuildConfig.API_URL}/albums/$albumId/compositions"
    )

    suspend fun getAlbumByTitle(title: String) =
        client.get<Album>("${BuildConfig.API_URL}/albums/$title")

    suspend fun getAlbums(): List<Album> = client.get("${BuildConfig.API_URL}/albums/popular")
}