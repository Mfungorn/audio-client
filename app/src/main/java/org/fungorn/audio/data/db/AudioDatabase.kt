package org.fungorn.audio.data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import org.fungorn.audio.data.db.dao.AuthorDao
import org.fungorn.audio.data.db.dao.TrackDao
import org.fungorn.audio.domain.model.AuthorEntity
import org.fungorn.audio.domain.model.TrackEntity

@Database(
    entities = [
        AuthorEntity::class,
        TrackEntity::class
    ],
    version = 1,
    exportSchema = true
)
abstract class AudioDatabase : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
    abstract fun trackDao(): TrackDao

    companion object {
        const val NAME = "audio"
    }
}