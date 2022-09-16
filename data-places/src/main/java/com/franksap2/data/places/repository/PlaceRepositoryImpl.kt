package com.franksap2.data.places.repository

import com.franksap2.data.places.model.Place
import com.franksap2.data.places.model.PlaceNetwork
import com.franksap2.data.places.model.TopPlace
import com.franksap2.data.places.model.TopPlaceNetwork
import com.franksap2.data.places.model.asExternal
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

internal class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {

    override fun collectPlaces(): Flow<List<Place>> = flow {
        emit(fakePlaces().map { it.asExternal() })
    }.flowOn(Dispatchers.IO)

    override fun collectTopPlaces(): Flow<List<TopPlace>> = flow {
        emit(fakeTopPlaces().map { it.asExternal() })
    }.flowOn(Dispatchers.IO)


}

private fun fakePlaces() = listOf(
    PlaceNetwork(
        id = "1",
        img = "https://source.unsplash.com/DtWyp_4YEes",
        country = "Maldives",
        place = "Noonu Atoll",
        category = "Beach"
    )
)

private fun fakeTopPlaces() = listOf(
    TopPlaceNetwork(
        id = "1",
        img = "https://source.unsplash.com/DtWyp_4YEes",
        country = "Maldives",
        place = "Noonu Atoll"
    ),
    TopPlaceNetwork(
        id = "2",
        img = "https://source.unsplash.com/DtWyp_4YEes",
        country = "Maldives",
        place = "Noonu Atoll"
    ),
    TopPlaceNetwork(
        id = "3",
        img = "https://source.unsplash.com/DtWyp_4YEes",
        country = "Maldives",
        place = "Noonu Atoll"
    ),
    TopPlaceNetwork(
        id = "4",
        img = "https://source.unsplash.com/DtWyp_4YEes",
        country = "Maldives",
        place = "Noonu Atoll"
    )
)