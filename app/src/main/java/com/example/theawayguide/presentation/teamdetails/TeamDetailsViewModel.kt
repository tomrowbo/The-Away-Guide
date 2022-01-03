package com.example.theawayguide.presentation.teamdetails

import android.content.Context
import android.content.SharedPreferences
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
import com.example.theawayguide.R
import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel
@Inject
constructor(
    private val teamRepository: TeamRepository,
    private val savedStateHandle: SavedStateHandle,
    private val sharedPreferences: SharedPreferences?,
    private val context: Context // Mitigated warning in IDE
) : ViewModel() {

    var uiState: MutableState<TeamDetailsUiState> = mutableStateOf(TeamDetailsUiState())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    var errorState: MutableState<Boolean> = mutableStateOf(false)

    var teamUrl: String? = null

    var radius: Int = context.resources.getInteger(R.integer.default_attraction_distance)

    init {
        savedStateHandle.get<String>("teamUrl")?.let { url ->
            teamUrl = url
            getScreenInfo()
        }
    }

    private fun getScreenInfo() {
        viewModelScope.launch {
            loadingState.value = true
            radius = sharedPreferences?.getInt(context.getString(R.string.attraction_distance_radius_key), context.resources.getInteger(R.integer.default_attraction_distance)) ?: context.resources.getInteger(R.integer.default_attraction_distance)
            try {
                val team = teamUrl?.let { teamRepository.getTeamDetails(it) }
                var pubList: List<AttractionSummaryUiState>? = null
                var restaurantList: List<AttractionSummaryUiState>? = null
                var hotelList: List<AttractionSummaryUiState>? = null

                if (team != null) {
                    if (team.mapsLatitude != null && team.mapsLongitude != null) {
                        hotelList = getHotels(team.mapsLatitude, team.mapsLongitude)
                        pubList = getPubs(team.mapsLatitude, team.mapsLongitude)
                        restaurantList = getRestaurants(team.mapsLatitude, team.mapsLongitude)
                    }
                }

                uiState.value = TeamDetailsUiState(
                    team = team,
                    pubList = pubList,
                    hotelList = hotelList,
                    restaurantList = restaurantList
                )

                loadingState.value = false
            } catch (e: Exception) {
                errorState.value = true
                loadingState.value = false
            }
        }
    }

    fun retry() {
        errorState.value = false
        getScreenInfo()
    }

    private suspend fun getHotels(mapsLatitude: Double, mapsLongitude: Double): List<AttractionSummaryUiState>? {
        return teamRepository.getHotels(
            mapsLatitude,
            mapsLongitude,
            radius
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }
    }

    private suspend fun getPubs(mapsLatitude: Double, mapsLongitude: Double): List<AttractionSummaryUiState>? {
        return teamRepository.getPubs(
            mapsLatitude,
            mapsLongitude,
            radius
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
        }
    }

    private suspend fun getRestaurants(mapsLatitude: Double, mapsLongitude: Double): List<AttractionSummaryUiState>? {
        return teamRepository.getRestaurants(
            mapsLatitude,
            mapsLongitude,
            radius
        )?.map { attraction ->
            mapToAttractionUiModel(attraction)
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

    private fun mapToAttractionUiModel(attraction: Attraction): AttractionSummaryUiState {
        return AttractionSummaryUiState(
            attraction.name ?: "",
            mapToImageUrl(attraction.imageUrl),
            mapRating(attraction.rating),
            attraction.totalRatings ?: -1,
            attraction.address,
            attraction.placeId
        )
    }

    private fun mapToImageUrl(imageUrl: String?): String {
        return "https://maps.googleapis.com/maps/api/place/" +
            "photo?maxwidth=3840&" +
            "photo_reference=$imageUrl" +
            "&key=${BuildConfig.MAPS_API_KEY}"
    }
}
