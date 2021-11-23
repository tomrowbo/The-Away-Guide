package com.example.theawayguide.presentation.teamdetails

import androidx.compose.ui.graphics.vector.ImageVector
import com.example.theawayguide.domain.Team

data class TeamDetailsUiModel(
    var team: Team? = null,
    var hotelList: List<AttractionSummaryUiState>? = null,
    var pubList: List<AttractionSummaryUiState>? = null,
    var restaurantList: List<AttractionSummaryUiState>? = null,
)

data class AttractionSummaryUiState(
    val name: String? = "",
    val imageUrl: String? = null,
    val ratings: List<ImageVector>? = emptyList(),
    val totalRatings: Int? = null,
    val shortAddress: String? = "",
)
