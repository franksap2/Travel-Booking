package com.franksap2.data.places.model

internal data class PlaceNetwork(
    val id: String,
    val img: String,
    val country: String,
    val place: String,
    val category: String
)

internal fun PlaceNetwork.asExternal() = Place(id, img, country, place, category)