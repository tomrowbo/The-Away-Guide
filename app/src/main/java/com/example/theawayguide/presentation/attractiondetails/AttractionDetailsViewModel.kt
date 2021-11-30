package com.example.theawayguide.presentation.attractiondetails

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.StarBorder
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theawayguide.BuildConfig
import com.example.theawayguide.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

const val RADIUS = 1500

@HiltViewModel
class AttractionDetailsViewModel
@Inject
constructor(
    private val teamRepository: TeamRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState: MutableState<AttractionDetailsUiState> = mutableStateOf(AttractionDetailsUiState())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<String>("attractionId")?.let { placeId ->
            getScreenInfo(placeId)
        }
    }

    private fun getScreenInfo(placeId: String) {
        viewModelScope.launch {
            loadingState.value = true

            uiState.value = mapToAttractionUiState(placeId)

            loadingState.value = false
        }
    }

    private suspend fun mapToAttractionUiState(placeId: String): AttractionDetailsUiState {
        val attraction = teamRepository.getAttractionDetails(placeId)
        if (attraction != null) {
            return AttractionDetailsUiState(
                name = attraction.name,
                imageUrl = mapToImageUrl(attraction.imageUrl),
                address = attraction.address,
                ratingIcons = mapRating(attraction.rating),
                totalRatings = attraction.totalRatings,
                phoneNumber = attraction.phoneNumber,
                website = attraction.website,
                openNow = mapOpenNow(attraction.openNow),
                openingHours = attraction.openingHours,
                priceLevel = mapPriceLevel(attraction.priceLevel)
            )
        }
        return AttractionDetailsUiState()
    }

    private fun mapPriceLevel(priceLevel: Int?): String? {
        return priceLevel?.let { "£".repeat(it) }
    }

    private fun mapOpenNow(openNow: Boolean?): String? {
        return when (openNow) {
            true -> "Open Now"
            false -> "Closed Now"
            null -> null
        }
    }

    private fun mapRating(rating: Double?): List<ImageVector>? {
        if (rating != null) {
            val ratingIcons: ArrayList<ImageVector> = ArrayList()
            for (i in 1..5) {
                ratingIcons.add(
                    when {
                        rating >= i -> Icons.Outlined.Star
                        else -> Icons.Outlined.StarBorder
                    }
                )
            }
            return ratingIcons
        }
        return null
    }

    private fun mapToImageUrl(imageUrl: String?): String {
        return "https://maps.googleapis.com/maps/api/place/" +
            "photo?maxwidth=3840&" +
            "photo_reference=$imageUrl" +
            "&key=${BuildConfig.MAPS_API_KEY}"
    }
}
