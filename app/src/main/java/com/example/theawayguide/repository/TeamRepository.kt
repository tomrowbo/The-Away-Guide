package com.example.theawayguide.repository

import com.example.theawayguide.domain.Team

interface TeamRepository {

    suspend fun getAll(): List<Team>?

    suspend fun getTeamDetails(url: String): Team?

}
