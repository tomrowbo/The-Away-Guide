package com.example.theawayguide.repository

import com.example.theawayguide.domain.Team
import com.example.theawayguide.network.FirebaseService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TeamRepositoryImpl(
    private val firebaseService: FirebaseService
) : TeamRepository {

    override suspend fun getAll(): List<Team>? {
        return withContext(Dispatchers.IO) {
            firebaseService.getAllTeams()
        }
    }

    override suspend fun getByUrl(url: String): Team {
        TODO("Not yet implemented")
    }
}
