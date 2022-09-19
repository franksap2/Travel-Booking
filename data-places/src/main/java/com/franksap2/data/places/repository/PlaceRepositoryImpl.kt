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
import kotlinx.coroutines.withContext

internal class PlaceRepositoryImpl @Inject constructor() : PlaceRepository {

    override fun collectPlaces(): Flow<List<Place>> = flow {
        emit(fakePlaces().map { it.asExternal() })
    }.flowOn(Dispatchers.IO)

    override fun collectTopPlaces(): Flow<List<TopPlace>> = flow {
        emit(fakeTopPlaces().map { it.asExternal() })
    }.flowOn(Dispatchers.IO)


    override suspend fun getPlaceById(id: String): Place {
        return withContext(Dispatchers.IO) {
            fakePlaces().first { it.id == id }.asExternal()
        }
    }

}

private fun fakePlaces() = listOf(
    PlaceNetwork(
        id = "1",
        img = "https://source.unsplash.com/DtWyp_4YEes",
        country = "Maldives",
        place = "Noonu Atoll",
        category = "Beach",
        price = 599.99f,
        description = "Noonu Atoll, also known as Southern Miladhunmadulu Atoll or Miladhunmadulu Dhekunuburi, is an administrative division of the Maldives corresponding to the southern section of Miladhunmadulu Atoll."
    ),
    PlaceNetwork(
        id = "2",
        img = "https://source.unsplash.com/t1Cm0nrCjDY",
        country = "Italy",
        place = "Metropolitan city of Rome",
        category = "Cites",
        price = 199.99f,
        description = "Metropolitan City of Rome Capital is an area of local government at the level of metropolitan city in the Lazio region of the Republic of Italy. It comprises the territory of the city of Rome and 120 other municipalities in the hinterland of the city. With more than 4.3 million inhabitants, it is the largest metropolitan city in Italy."
    ),
    PlaceNetwork(
        id = "3",
        img = "https://source.unsplash.com/CRMjqDZwxS4",
        country = "Spain",
        place = "Madrid",
        category = "Cites",
        price = 199.99f,
        description = "Madrid is the capital and most populous city of Spain. The city has almost 3.4 million inhabitants and a metropolitan area population of approximately 6.7 million. It is the second-largest city in the European Union, and its monocentric metropolitan area is the second-largest in the EU"
    ),
    PlaceNetwork(
        id = "4",
        img = "https://source.unsplash.com/UCd78vfC8vU",
        country = "United States",
        place = "Yosemite",
        category = "Forest",
        price = 99.99f,
        description = "Yosemite National Park is an American national park in California, surrounded on the southeast by Sierra National Forest and on the northwest by Stanislaus National Forest. The park is managed by the National Park Service and covers an area of 759,620 acres. Designated a World Heritage Site in 1984, Yosemite is internationally recognized for its granite cliffs, waterfalls, clear streams, giant sequoia groves, lakes, mountains, meadows, glaciers, and biological diversity.[8] Almost 95 percent of the park is designated wilderness.[9] Yosemite is one of the largest and least fragmented habitat blocks in the Sierra Nevada, and the park supports a diversity of plants and animals."
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