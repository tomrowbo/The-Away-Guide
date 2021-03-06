package com.example.theawayguide.repository

import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team

interface TeamRepository {

    suspend fun retrieveTeams(): List<Team>?

    suspend fun getTeamDetails(url: String): Team

    suspend fun getRestaurants(latitude: Double, longitude: Double, radius: Int): List<Attraction>?

    suspend fun getHotels(latitude: Double, longitude: Double, radius: Int): List<Attraction>?

    suspend fun getPubs(latitude: Double, longitude: Double, radius: Int): List<Attraction>?

    suspend fun getAttractionDetails(placeId: String): Attraction?

    fun getTeamsByLeague(leagueId: String): List<Team>

    fun getAllTeams(): List<Team>

    suspend fun getLeagues(): List<League>?
}
