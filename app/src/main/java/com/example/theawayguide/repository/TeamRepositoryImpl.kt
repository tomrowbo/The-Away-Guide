package com.example.theawayguide.repository

import com.example.theawayguide.BuildConfig
import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team
import com.example.theawayguide.network.FirebaseService
import com.example.theawayguide.network.PlaceDTO
import com.example.theawayguide.network.Result
import com.example.theawayguide.network.RetrofitService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRepositoryImpl(
    private val firebaseService: FirebaseService,
    private val retrofitService: RetrofitService
) : TeamRepository {

    private var allTeams = emptyList<Team>()

    override suspend fun retrieveTeams(): List<Team> {
        return withContext(Dispatchers.IO) {
            allTeams = firebaseService.getAllTeams()
            allTeams
        }
    }

    override suspend fun getTeamDetails(url: String): Team {
        return withContext(Dispatchers.IO) {
            firebaseService.getTeamDetails(teamUrl = url)
        }
    }

    override suspend fun getRestaurants(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<Attraction>? {
        return retrofitService.nearbyPlaceSearch(
            "$latitude,$longitude",
            radius,
            "restaurant",
            BuildConfig.MAPS_API_KEY
        )
            .results?.map {
                mapToAttractionSummary(it)
            }?.reversed()
    }

    override suspend fun getHotels(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<Attraction>? {
        return retrofitService.nearbyPlaceSearch(
            "$latitude,$longitude",
            radius,
            "lodging",
            BuildConfig.MAPS_API_KEY
        )
            .results?.map {
                mapToAttractionSummary(it)
            }?.reversed()
    }

    override suspend fun getPubs(
        latitude: Double,
        longitude: Double,
        radius: Int
    ): List<Attraction>? {
        return retrofitService.nearbyPlaceSearch(
            "$latitude,$longitude",
            radius,
            "bar",
            BuildConfig.MAPS_API_KEY
        )
            .results?.map {
                mapToAttractionSummary(it)
            }?.reversed()
    }

    private fun mapToAttractionSummary(placeDTO: PlaceDTO): Attraction {
        return Attraction(
            placeDTO.name,
            placeDTO.photos?.get(0)?.photoReference,
            placeDTO.vicinity,
            placeDTO.rating,
            placeDTO.placeId,
            placeDTO.userRatingsTotal
        )
    }

    override suspend fun getAttractionDetails(placeId: String): Attraction? {
        return retrofitService.placeDetailsSearch(
            placeId,
            BuildConfig.MAPS_API_KEY
        ).result?.let {
            mapToAttraction(
                it
            )
        }
    }

    override fun getTeamsByLeague(leagueId: String): List<Team> {
        return allTeams.filter { it.league == leagueId }
    }

    override fun getAllTeams(): List<Team> {
        return allTeams
    }

    override suspend fun getLeagues(): List<League> {
        return withContext(Dispatchers.IO) {
            firebaseService.getLeagues()
        }
    }

    private fun mapToAttraction(dto: Result): Attraction {
        return Attraction(
            name = dto.name,
            imageUrl = dto.photos?.get(0)?.photoReference,
            address = dto.formattedAddress,
            rating = dto.rating,
            totalRatings = dto.userRatingsTotal,
            priceLevel = dto.priceLevel,
            phoneNumber = dto.formattedPhoneNumber,
            website = dto.website,
            openNow = dto.openingHours?.openNow,
            openingHours = dto.openingHours?.weekdayText
        )
    }
}
