package org.fungorn.audio.domain.model

data class Album(
    val id: Long,
    val title: String,
    val cover: String,
    val rating: Int,
    val price: Int,
    val authorName: String?,
    val tracksCount: Int?,
    val genre: String
)