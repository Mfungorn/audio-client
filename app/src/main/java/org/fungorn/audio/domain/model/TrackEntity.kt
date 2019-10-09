package org.fungorn.audio.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "track")
data class TrackEntity(
    @PrimaryKey val id: Long,
    val title: String,
    val price: Int,
    val duration: Int,
    val rating: Int
)