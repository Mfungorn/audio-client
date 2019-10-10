package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import org.fungorn.audio.domain.model.Album

class AlbumApi(
    private val client: HttpClient
) {
    suspend fun getAlbum(albumId: Long) = null

    suspend fun getAlbums(): List<Album> = listOf()
}