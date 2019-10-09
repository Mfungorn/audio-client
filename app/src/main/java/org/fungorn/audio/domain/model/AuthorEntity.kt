package org.fungorn.audio.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author")
data class AuthorEntity(
    @PrimaryKey val id: Long,
    val name: String,
    val rating: Int = 0
)