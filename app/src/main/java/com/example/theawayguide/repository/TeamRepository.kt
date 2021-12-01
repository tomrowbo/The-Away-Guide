package com.example.theawayguide.repository

import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team

interface TeamRepository {

    suspend fun getAll(): List<Team>?

    suspend fun getTeamDetails(url: String): Team

    suspend fun getRestaurants(latitude: Double, longitude: Double, radius: Int): List<Attraction>?

    suspend fun getHotels(latitude: Double, longitude: Double, radius: Int): List<Attraction>?

    suspend fun getPubs(latitude: Double, longitude: Double, radius: Int): List<Attraction>?

    suspend fun getAttractionDetails(placeId: String): Attraction?

    suspend fun getTeamsByLeague(leagueId: String): List<Team>?

    suspend fun getAllLeagues(): List<League>?
}
