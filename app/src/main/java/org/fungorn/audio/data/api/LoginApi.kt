package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import io.ktor.client.call.call
import io.ktor.client.call.receive
import io.ktor.http.ContentType
import io.ktor.http.HttpMethod
import io.ktor.http.contentType
import org.fungorn.audio.BuildConfig
import org.fungorn.audio.domain.model.ApiResponse
import org.fungorn.audio.domain.model.AuthResponse
import org.fungorn.audio.domain.model.LoginRequest
import org.fungorn.audio.domain.model.SignUpRequest

class LoginApi(private val client: HttpClient) {
    suspend fun signUp(request: SignUpRequest): ApiResponse {
        val call = client.call(
            "${BuildConfig.API_URL}/auth/register/customer"
        ) {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            body = request
        }
        return call.response.receive<ApiResponse>()
    }

    suspend fun signIn(request: LoginRequest): AuthResponse {
        val call = client.call(
            "${BuildConfig.API_URL}/auth/login"
        ) {
            method = HttpMethod.Post
            contentType(ContentType.Application.Json)
            body = request
        }
        return call.response.receive<AuthResponse>()
    }
}