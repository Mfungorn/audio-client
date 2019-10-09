package org.fungorn.audio.data.db.dao

import androidx.room.*
import org.fungorn.audio.domain.model.TrackEntity

@Dao
interface TrackDao {
    @Query("SELECT * FROM track")
    fun getAll(): List<TrackEntity>

    @Query("SELECT * FROM track WHERE id = :id")
    fun getById(id: Long): TrackEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: TrackEntity)

    @Update
    fun update(entity: TrackEntity)

    @Delete
    fun delete(entity: TrackEntity)

    @Query("DELETE FROM track")
    fun deleteAll()
}