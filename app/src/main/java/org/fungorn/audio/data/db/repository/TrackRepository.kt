package org.fungorn.audio.data.db.repository

import org.fungorn.audio.data.db.dao.TrackDao
import org.fungorn.audio.domain.model.Track
import org.fungorn.audio.domain.model.TrackEntity

class TrackRepository(
    private val trackDao: TrackDao
) {
    fun add(track: Track) = trackDao.insert(toTrackEntity(track))
    fun getAll() = trackDao.getAll().map { toTrackModel(it) }
    fun existsById(id: Long) = trackDao.count(id) > 0
    fun deleteAll() = trackDao.deleteAll()

    private fun toTrackEntity(track: Track) = TrackEntity(
        id = track.id,
        title = track.title,
        price = track.price,
        duration = track.duration,
        rating = track.rating,
        authorName = track.authorName,
        genre = track.genre
    )

    private fun toTrackModel(entity: TrackEntity) = Track(
        id = entity.id,
        title = entity.title,
        price = entity.price,
        duration = entity.duration,
        rating = entity.rating,
        authorName = entity.authorName,
        genre = entity.genre,
        cover = null
    )
}