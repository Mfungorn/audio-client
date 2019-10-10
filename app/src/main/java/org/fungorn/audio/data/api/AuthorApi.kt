package org.fungorn.audio.data.api

import io.ktor.client.HttpClient
import org.fungorn.audio.domain.model.Author

class AuthorApi(
    private val client: HttpClient
) {
    suspend fun getAuthor(authorId: Long) = null
    suspend fun getAuthorByName(name: String) = null

    suspend fun getAuthors(): List<Author> = listOf()

    suspend fun getAuthorsWithNameStartsWith(query: String): List<Author> = listOf()
}