package com.example.theawayguide.domain

data class Attraction(
    val name: String? = "",
    val imageUrl: String? = "",
    val address: String? = "",
    val rating: Double? = null,
    val tags: List<String>? = null,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val totalRatings: Int? = null,
    val priceLevel: Int? = null
)