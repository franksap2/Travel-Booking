package com.franksap2.feature.home.model

import com.franksap2.data.places.model.Place
import com.franksap2.data.places.model.TopPlace

data class HomeUiState(
    val places: List<Place> = emptyList(),
    val topPlaces: List<TopPlace> = emptyList()
)
