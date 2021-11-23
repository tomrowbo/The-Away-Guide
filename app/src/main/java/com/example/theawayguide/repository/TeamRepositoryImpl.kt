package com.example.theawayguide.repository

import com.example.theawayguide.BuildConfig
import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.domain.Team
import com.example.theawayguide.network.FirebaseService
import com.example.theawayguide.network.PlaceDTO
import com.example.theawayguide.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRepositoryImpl(
    private val firebaseService: FirebaseService,
    private val retrofitService: RetrofitService
) : TeamRepository {

    override suspend fun getAll(): List<Team> {
        return withContext(Dispatchers.IO) {
            firebaseService.getAllTeams()
        }
    }

    override suspend fun getTeamDetails(url: String): Team {
        return withContext(Dispatchers.IO) {
            firebaseService.getTeamDetails(teamUrl = url)
        }
    }

    override suspend fun getRestaurants(latitude: Double, longitude: Double, radius: Int): List<Attraction>? {
        return retrofitService.nearbyPlaceSearch(
            "$latitude,$longitude",
            radius,
            "restaurant",
            BuildConfig.MAPS_API_KEY
        )
            .results?.map {
                mapToAttraction(it)
            }
    }

    override suspend fun getHotels(latitude: Double, longitude: Double, radius: Int): List<Attraction>? {
        return retrofitService.nearbyPlaceSearch(
            "$latitude,$longitude",
            radius,
            "hotel",
            BuildConfig.MAPS_API_KEY
        )
            .results?.map {
                mapToAttraction(it)
            }
    }

    override suspend fun getPubs(latitude: Double, longitude: Double, radius: Int): List<Attraction>? {
        return retrofitService.nearbyPlaceSearch(
            "$latitude,$longitude",
            radius,
            "pub",
            BuildConfig.MAPS_API_KEY
        )
            .results?.map {
                mapToAttraction(it)
            }
    }

    private fun mapToAttraction(placeDTO: PlaceDTO): Attraction {
        return Attraction(
            placeDTO.name,
            placeDTO.photos?.get(0)?.photoReference,
            placeDTO.vicinity,
            placeDTO.rating,
            placeDTO.types,
            placeDTO.geometry?.location?.lat,
            placeDTO.geometry?.location?.lng,
            placeDTO.userRatingsTotal,
            placeDTO.priceLevel
        )
    }
}
