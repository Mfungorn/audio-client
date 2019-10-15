package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.Track

class TrackApi(private val client: HttpClient) {
    suspend fun getTrack(trackId: Long) = client.get<Track>(
        "${BuildConfig.API_URL}/compositions/$trackId"
    )

    suspend fun getTracks(): List<Track> = client.get(
        "${BuildConfig.API_URL}/compositions/popular"
    )

    suspend fun getTracksWithTitleStartsWith(query: String): List<Track> = client.get(
        "${BuildConfig.API_URL}/compositions/search"
    ) {
        parameter("q", query)
    }
}