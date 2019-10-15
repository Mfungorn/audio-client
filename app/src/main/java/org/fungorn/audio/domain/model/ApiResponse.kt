package org.fungorn.audio.domain.model

import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)