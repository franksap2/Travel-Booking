package com.franksap2.data.places.model

internal data class PlaceNetwork(
    val id: String,
    val img: String,
    val country: String,
    val place: String,
    val category: String,
    val price: Float,
    val description: String
)

internal fun PlaceNetwork.asExternal() = Place(
    id = id,
    img = img,
    country = country,
    place = place,
    category = category,
    price = price,
    description = description
)