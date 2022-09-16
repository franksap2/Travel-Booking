package com.franksap2.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franksap2.data.places.repository.PlaceRepository
import com.franksap2.feature.home.model.HomeUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    placeRepository: PlaceRepository
) : ViewModel() {

    val homeUiState: StateFlow<HomeUiState> = combine(
        placeRepository.collectPlaces(),
        placeRepository.collectTopPlaces()
    ) { places, topPlaces ->
        HomeUiState(places, topPlaces)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = HomeUiState()
    )


}