package com.franksap2.feature.home

import androidx.lifecycle.ViewModel
import com.franksap2.data.places.repository.PlaceRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val placeRepository: PlaceRepository
) : ViewModel() {

}