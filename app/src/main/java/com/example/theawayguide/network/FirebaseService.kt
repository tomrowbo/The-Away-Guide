package com.example.theawayguide.network

import android.util.Log
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

    // TODO: Move to external mapper or move logic to the repository?
    private fun mapToTeam(teamSnapshot: DataSnapshot): Team {
        val team = Team(
            teamSnapshot.child("TeamName").value as String?,
            teamSnapshot.key,
            teamSnapshot.child("BadgeURL").value as String?,
            teamSnapshot.child("StadiumName").value as String?,
        )
        return team
    }
}
