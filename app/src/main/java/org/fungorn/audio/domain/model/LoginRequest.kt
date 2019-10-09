package org.fungorn.audio.domain.model

data class LoginRequest(
    val email: String,
    val password: String
)