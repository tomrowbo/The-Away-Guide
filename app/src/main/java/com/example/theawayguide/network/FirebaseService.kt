package com.example.theawayguide.network

import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team
import com.google.android.gms.tasks.Tasks
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.FirebaseDatabase

class FirebaseService constructor(
    private val database: FirebaseDatabase
) {

    fun getAllTeams(): List<Team> {
        val getTeamsTask = database.getReference("Teams").get()
        Tasks.await(getTeamsTask)
        return getTeamsTask.result.children.map { team ->
            mapToTeam(team)
        }
    }

    fun getTeamDetails(teamUrl: String): Team {
        val database = FirebaseDatabase.getInstance()
        val getTeamTask = database.getReference("Teams").child(teamUrl).get()
        val getTeamFurtherInfoTask = database.getReference("TeamDetails").child(teamUrl).get()
        Tasks.await(getTeamTask)
        Tasks.await(getTeamFurtherInfoTask)
        return mapToDetailedTeam(mapToTeam(getTeamTask.result), getTeamFurtherInfoTask.result)
    }

    fun getLeagues(): List<League> {
        val database = FirebaseDatabase.getInstance()
        val getLeaguesTask = database.getReference("Leagues").get()
        Tasks.await(getLeaguesTask)
        return getLeaguesTask.result.children.map { league ->
            mapToLeague(league)
        }
    }

    private fun mapToLeague(leagueSnapshot: DataSnapshot): League {
        return League(
            name = leagueSnapshot.child("Name").value as String?,
            badgeUrl = leagueSnapshot.child("BadgeURL").value as String?,
            id = leagueSnapshot.key
        )
    }

    private fun mapToTeam(teamSnapshot: DataSnapshot): Team {
        return Team(
            name = teamSnapshot.child("TeamName").value as String?,
            url = teamSnapshot.key,
            badgeUrl = teamSnapshot.child("BadgeURL").value as String?,
            stadiumName = teamSnapshot.child("StadiumName").value as String?,
            league = teamSnapshot.child("League").value as String?
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
