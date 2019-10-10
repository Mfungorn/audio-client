package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import org.fungorn.audio.domain.model.Track

class TrackApi(private val client: HttpClient) {
    suspend fun getTrack(trackId: Long) = null

    suspend fun getTracks(): List<Track> = listOf()

    suspend fun getTracksWithTitleStartsWith(query: String): List<Track> = listOf()
}