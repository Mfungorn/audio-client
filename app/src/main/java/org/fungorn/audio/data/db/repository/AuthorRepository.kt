package org.fungorn.audio.data.db.repository

import org.fungorn.audio.data.db.dao.AuthorDao
import org.fungorn.audio.domain.model.Author
import org.fungorn.audio.domain.model.AuthorEntity

class AuthorRepository(
    private val authorDao: AuthorDao
) {
    fun add(author: Author) = authorDao.insert(toAuthorEntity(author))
    fun getAll() = authorDao.getAll().map { toAuthorModel(it) }
    fun deleteAll() = authorDao.deleteAll()

    private fun toAuthorEntity(author: Author) = AuthorEntity(
        id = author.id,
        name = author.name,
        rating = author.rating
    )

    private fun toAuthorModel(entity: AuthorEntity) = Author(
        id = entity.id,
        name = entity.name,
        rating = entity.rating
    )
}