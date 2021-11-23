package com.example.theawayguide.presentation.teamdetails

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
import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

const val RADIUS = 1500

@HiltViewModel
class TeamDetailsViewModel
@Inject
constructor(
    private val teamRepository: TeamRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiModel: MutableState<TeamDetailsUiModel> = mutableStateOf(TeamDetailsUiModel())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<String>("teamUrl")?.let { teamUrl ->
            getTeamDetails(teamUrl)
        }
    }

    private fun getTeamDetails(teamUrl: String) {
        viewModelScope.launch {
            loadingState.value = true

            uiModel.value.team = teamRepository.getTeamDetails(teamUrl)

            uiModel.value.team?.let {
                if (it.mapsLatitude != null && it.mapsLongitude != null) {
                    getAttractions(it.mapsLatitude, it.mapsLongitude, RADIUS)
                }
            }

            loadingState.value = false
        }
    }

    private suspend fun getAttractions(latitude: Double, longitude: Double, radius: Int) {
        uiModel.value.hotelList =
            teamRepository.getHotels(
                latitude,
                longitude,
                radius
            )?.map { attraction ->
                mapToAttractionUiModel(attraction)
            }

        uiModel.value.pubList = teamRepository.getPubs(
            latitude,
            longitude,
            radius
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }

        uiModel.value.restaurantList = teamRepository.getRestaurants(
            latitude,
            longitude,
            radius
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }
    }

    private fun mapToAttractionUiModel(attraction: Attraction): AttractionSummaryUiState {
        val rating = attraction.rating ?: 0.0
        val ratingIcons = ArrayList<ImageVector>()
        for (i in 1..5) {
            ratingIcons.add(
                when {
                    rating >= i -> Icons.Outlined.Star
                    else -> Icons.Outlined.StarBorder
                }
            )
        }
        return AttractionSummaryUiState(
            attraction.name ?: "",
            "https://maps.googleapis.com/maps/api/place/" +
            "photo?maxwidth=3840&" +
            "photo_reference=${attraction.imageUrl}" +
            "&key=${BuildConfig.MAPS_API_KEY}" ?: "",
            ratingIcons,
            attraction.totalRatings ?: -1,
            attraction.address
        )
    }
}
