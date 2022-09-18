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
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

@HiltViewModel
class DetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val placeRepository: PlaceRepository
) : ViewModel() {

    private val placeId = savedStateHandle.get<String>(PLACE_ID).orEmpty()

    private val _placeState = MutableStateFlow<Place?>(null)
    val placeState: StateFlow<Place?> = _placeState.asStateFlow()

    init {
        viewModelScope.launch {
            _placeState.value = placeRepository.getPlaceById(placeId)
        }
    }
}