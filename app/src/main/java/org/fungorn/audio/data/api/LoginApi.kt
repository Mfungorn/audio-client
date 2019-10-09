package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import org.fungorn.audio.domain.model.LoginRequest

class LoginApi(private val client: HttpClient) {
    suspend fun signIn(request: LoginRequest): String {
        return "token"
    }
}