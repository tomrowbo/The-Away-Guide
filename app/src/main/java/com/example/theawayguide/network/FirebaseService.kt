package com.example.theawayguide.network

import com.example.theawayguide.domain.Team
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase
import okhttp3.internal.Util.immutableList

object FirebaseService {
    private val DATABASE = FirebaseDatabase.getInstance()

    fun getAllTeams(): MutableList<Team>? {
        val teams = immutableList<Team>()
        FirebaseDatabase.getInstance().getReference("Teams").get().addOnSuccessListener {
            allTeams ->
            if (allTeams.exists()) {
                allTeams.children.map {
                    team ->
                    teams.add(
                        mapToTeam(team)
                    )
                }
            } else {
                println("Error")
            }
        }.addOnFailureListener {
            println("Error")
        }
        return teams
    }

    private fun mapToTeam(team: DataSnapshot): Team {
        return Team(
            team.child("TeamName").value as String?,
            team.key,
            team.child("BadgeURL").value as String?,
            team.child("StadiumName").value as String?,
        )
    }
}
