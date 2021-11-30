package com.example.theawayguide.domain

data class Attraction(
    val name: String? = "",
    val imageUrl: String? = "",
    val address: String? = "",
    val rating: Double? = null,
    val placeId: String? = "",
    val totalRatings: Int? = null,
    val priceLevel: Int? = null,
    val tags: List<String>? = emptyList(),
    val phoneNumber: String? = "",
    val website: String? = "",
    val openNow: Boolean? = null,
    val openingHours: List<String>? = emptyList()
)
