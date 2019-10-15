package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.FavoritesResponse

class FavoritesApi(private val client: HttpClient) {
    suspend fun loadFavorites(): FavoritesResponse = client.get(
        "${BuildConfig.API_URL}/user/favorites"
    )
}