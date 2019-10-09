package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import org.fungorn.audio.domain.model.FavoritesResponse

class FavoritesApi(private val client: HttpClient) {
    suspend fun loadFavorites(): FavoritesResponse {
        return FavoritesResponse(arrayListOf(), arrayListOf())
    }
}