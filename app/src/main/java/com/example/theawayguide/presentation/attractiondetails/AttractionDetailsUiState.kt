package com.example.theawayguide.presentation.attractiondetails

import androidx.compose.ui.graphics.vector.ImageVector
import java.util.Collections.emptyList

data class AttractionDetailsUiState(
    val name: String? = "",
    val imageUrl: String? = "",
    val address: String? = "",
    val ratingIcons: List<ImageVector>? = null,
    val totalRatings: Int? = null,
    val tags: List<String>? = emptyList(),
    val phoneNumber: String? = "",
    val website: String? = "",
    val openNow: String? = "",
    val openingHours: List<String>? = emptyList(),
    val priceLevel: String? = ""
)
