package com.franksap2.data.places.model

internal data class TopPlaceNetwork(
    val id: String,
    val img: String,
    val country: String,
    val place: String
)

internal fun TopPlaceNetwork.asExternal() = TopPlace(id,img, country, place)