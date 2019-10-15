package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.AuthResponse
import org.fungorn.audio.domain.model.LoginRequest
import org.fungorn.audio.domain.model.SignUpRequest

class LoginApi(private val client: HttpClient) {
    suspend fun signUp(request: SignUpRequest): Any = client.post(
        "${BuildConfig.API_URL}/auth/register/customer"
    ) {
        body = request
    }

    suspend fun signIn(request: LoginRequest): AuthResponse = client.post(
        "${BuildConfig.API_URL}/auth/login"
    ) {
        body = request
    }
}