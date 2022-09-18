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
    ),
    PlaceNetwork(
        id = "2",
        img = "https://source.unsplash.com/VcWIMPXiGlU",
        country = "Italy",
        place = "Metropolitan city of Rome",
        category = "Cites"
    ),
    PlaceNetwork(
        id = "3",
        img = "https://source.unsplash.com/gfD4hwudzJI",
        country = "Spain",
        place = "Madrid",
        category = "Cites"
    ),
    PlaceNetwork(
        id = "4",
        img = "https://source.unsplash.com/hDU0hkN4ZZI",
        country = "Brazil",
        place = "Foz do Iguaçu",
        category = "Forest"
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
        img = "https://source.unsplash.com/VcWIMPXiGlU",
        country = "Italy",
        place = "Metropolitan city of Rome"
    ),
    TopPlaceNetwork(
        id = "3",
        img = "https://source.unsplash.com/gfD4hwudzJI",
        country = "Spain",
        place = "Madrid"
    ),
    TopPlaceNetwork(
        id = "4",
        img = "https://source.unsplash.com/hDU0hkN4ZZI",
        country = "Brazil",
        place = "Foz do Iguaçu"
    )
)