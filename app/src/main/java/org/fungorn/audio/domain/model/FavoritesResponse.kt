package org.fungorn.audio.domain.model

import com.google.gson.annotations.SerializedName

data class FavoritesResponse(
    @SerializedName("favoriteAuthors") val favoriteAuthors: ArrayList<Author>,
    @SerializedName("favoriteCompositions") val favoriteCompositions: ArrayList<Track>
)