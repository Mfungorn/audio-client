package org.fungorn.audio.data.db.dao

import androidx.room.*
import org.fungorn.audio.domain.model.AuthorEntity

@Dao
interface AuthorDao {
    @Query("SELECT * FROM author")
    fun getAll(): List<AuthorEntity>

    @Query("SELECT * FROM author WHERE id = :id")
    fun getById(id: Long): AuthorEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: AuthorEntity)

    @Update
    fun update(entity: AuthorEntity)

    @Query("SELECT COUNT(*) FROM author WHERE id = :id")
    fun count(id: Long): Int

    @Query("SELECT COUNT(*) FROM author WHERE name = :name")
    fun count(name: String): Int

    @Delete
    fun delete(entity: AuthorEntity)

    @Query("DELETE FROM author")
    fun deleteAll()
}