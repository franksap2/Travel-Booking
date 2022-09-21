package com.franksap2.feature.detail

import com.franksap2.data.places.model.Place

data class DetailUiState(
    val place: Place? = null,
    val detailType: DetailType = DetailType.Info
)

enum class DetailType{
    Info, Booking
}