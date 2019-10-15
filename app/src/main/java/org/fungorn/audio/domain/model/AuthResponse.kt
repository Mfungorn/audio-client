package org.fungorn.audio.domain.model

data class AuthResponse(
    val accessToken: String,
    val tokenType: String
)