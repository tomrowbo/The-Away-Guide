package com.example.theawayguide.repository

import com.example.theawayguide.domain.Attraction
import com.example.theawayguide.domain.Team

interface TeamRepository {

    suspend fun getAll(): List<Team>?

    suspend fun getTeamDetails(url: String): Team

    suspend fun getRestaurants(latitude: Int, longitude: Int, radius: Int): List<Attraction>?

    suspend fun getHotels(latitude: Int, longitude: Int, radius: Int): List<Attraction>?

    suspend fun getPubs(latitude: Int, longitude: Int, radius: Int): List<Attraction>?
}
