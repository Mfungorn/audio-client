package org.fungorn.audio.domain.model

data class Author(
    val id: Long,
    val name: String,
    val rating: Int,
    val logo: String = "",
    val bio: String = ""
)