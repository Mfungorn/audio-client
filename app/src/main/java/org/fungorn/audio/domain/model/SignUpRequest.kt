package org.fungorn.audio.domain.model

import com.google.gson.annotations.SerializedName


data class SignUpRequest(
    @SerializedName("name") var name: String,
    @SerializedName("email") var email: String,
    @SerializedName("password") var password: String
)