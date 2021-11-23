package com.example.theawayguide.network

import com.example.theawayguide.domain.Team
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

object FirebaseService {
    private val DATABASE = FirebaseDatabase.getInstance()

    fun getAllTeams(): List<Team> {
        val getTeamsTask = DATABASE.getReference("Teams").get()
        Tasks.await(getTeamsTask)
        return getTeamsTask.result.children.map { team ->
            mapToTeam(team)
        }
    }

    fun getTeamDetails(teamUrl: String): Team {
        val getTeamTask = DATABASE.getReference("Teams").child(teamUrl).get()
        val getTeamFurtherInfoTask = DATABASE.getReference("TeamDetails").child(teamUrl).get()
        Tasks.await(getTeamTask)
        Tasks.await(getTeamFurtherInfoTask)
        return mapToDetailedTeam(mapToTeam(getTeamTask.result), getTeamFurtherInfoTask.result)
    }

    private fun mapToTeam(teamSnapshot: DataSnapshot): Team {
        return Team(
            name = teamSnapshot.child("TeamName").value as String?,
            url = teamSnapshot.key,
            badgeUrl = teamSnapshot.child("BadgeURL").value as String?,
            stadiumName = teamSnapshot.child("StadiumName").value as String?,
        )
    }

    private fun mapToDetailedTeam(team: Team, teamSnapshot: DataSnapshot): Team {
        val mapsLocation = teamSnapshot.child("MapsLocation").value as String?
        val coords = mapsLocation?.split(",")
        return Team(
            name = team.name,
            stadiumName = team.stadiumName,
            description = teamSnapshot.child("Description").value as String?,
            imageUrl = teamSnapshot.child("ImageURL").value as String?,
            mapsLatitude = coords?.get(0)?.toDouble(),
            mapsLongitude = coords?.get(1)?.toDouble()
        )
    }
}
