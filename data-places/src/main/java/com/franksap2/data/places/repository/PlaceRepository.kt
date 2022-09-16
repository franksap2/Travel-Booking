package com.franksap2.data.places.repository

import com.franksap2.data.places.model.Place
import com.franksap2.data.places.model.TopPlace
import kotlinx.coroutines.flow.Flow

interface PlaceRepository {

    fun collectPlaces() : Flow<List<Place>>

    fun collectTopPlaces() : Flow<List<TopPlace>>

}