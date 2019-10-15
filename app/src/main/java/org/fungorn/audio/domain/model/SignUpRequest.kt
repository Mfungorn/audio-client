package org.fungorn.audio.domain.model

data class SignUpRequest(
    var name: String,
    var email: String,
    var password: String
)