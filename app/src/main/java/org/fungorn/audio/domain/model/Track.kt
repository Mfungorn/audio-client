package org.fungorn.audio.domain.model

data class Track(
    val id: Long,
    val title: String,
    val duration: Int,
    val text: String = "",
    val price: Int,
    val rating: Int,
    val cover: String?,
    val authorName: String,
    val genre: String
)