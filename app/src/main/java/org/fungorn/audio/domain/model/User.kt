package org.fungorn.audio.domain.model

data class User(
    val id: Long,
    val name: String,
    val email: String,
    var phone: String = "",
    var balance: Int = 0,
    val icon: String?
)