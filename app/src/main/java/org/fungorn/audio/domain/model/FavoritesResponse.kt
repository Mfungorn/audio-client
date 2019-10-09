package org.fungorn.audio.domain.model

import com.google.gson.annotations.SerializedName

data class FavoritesResponse(
    @SerializedName("favoriteAuthors") val authors: ArrayList<Author>,
    @SerializedName("favoriteCompositions") val tracks: ArrayList<Track>
)