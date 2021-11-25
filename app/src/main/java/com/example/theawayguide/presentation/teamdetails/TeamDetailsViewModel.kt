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
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiState: MutableState<TeamDetailsUiState> = mutableStateOf(TeamDetailsUiState())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<String>("teamUrl")?.let { teamUrl ->
            getScreenInfo(teamUrl)
        }
    }

    private fun getScreenInfo(teamUrl: String) {
        viewModelScope.launch {
            loadingState.value = true

            val team = teamRepository.getTeamDetails(teamUrl)
            var pubList: List<AttractionSummaryUiState>? = null
            var restaurantList: List<AttractionSummaryUiState>? = null
            var hotelList: List<AttractionSummaryUiState>? = null

            if (team.mapsLatitude != null && team.mapsLongitude != null) {
                hotelList = getHotels(team.mapsLatitude, team.mapsLongitude)
                pubList = getPubs(team.mapsLatitude, team.mapsLongitude)
                restaurantList = getRestaurants(team.mapsLatitude, team.mapsLongitude)
            }

            uiState.value = TeamDetailsUiState(
                team = team,
                pubList = pubList,
                hotelList = hotelList,
                restaurantList = restaurantList
            )

            loadingState.value = false
        }
    }

    private suspend fun getHotels(mapsLatitude: Double, mapsLongitude: Double): List<AttractionSummaryUiState>? {
        return teamRepository.getHotels(
            mapsLatitude,
            mapsLongitude,
            RADIUS
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }
    }

    private suspend fun getPubs(mapsLatitude: Double, mapsLongitude: Double): List<AttractionSummaryUiState>? {
        return teamRepository.getPubs(
            mapsLatitude,
            mapsLongitude,
            RADIUS
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }
    }

    private suspend fun getRestaurants(mapsLatitude: Double, mapsLongitude: Double): List<AttractionSummaryUiState>? {
        return teamRepository.getRestaurants(
            mapsLatitude,
            mapsLongitude,
            RADIUS
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }
    }

    private fun mapToAttractionUiModel(attraction: Attraction): AttractionSummaryUiState {
        var ratingIcons: ArrayList<ImageVector>? = null
        if (attraction.rating != null) {
            val rating = attraction.rating
            ratingIcons = ArrayList()
            for (i in 1..5) {
                ratingIcons.add(
                    when {
                        rating >= i -> Icons.Outlined.Star
                        else -> Icons.Outlined.StarBorder
                    }
                )
            }
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
