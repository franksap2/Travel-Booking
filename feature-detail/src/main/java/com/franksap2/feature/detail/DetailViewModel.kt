package com.franksap2.feature.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.franksap2.data.places.model.Place
import com.franksap2.data.places.repository.PlaceRepository
import com.franksap2.feature.detail.arguments.PLACE_ID
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val placeId = savedStateHandle.get<String>(PLACE_ID).orEmpty()

    private val placeState = MutableStateFlow<Place?>(null)
    private val detailType = MutableStateFlow(DetailType.Info)

    val uiState = combine(
        placeState,
        detailType
    ) { placeState, detailType ->
        DetailUiState(placeState, detailType)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DetailUiState()
    )

    init {
        viewModelScope.launch {
            placeState.value = placeRepository.getPlaceById(placeId)
        }
    }

    fun onBooking(){
        detailType.value = DetailType.Booking
    }
}