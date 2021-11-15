package com.example.theawayguide.network.util

import com.example.theawayguide.domain.Team
import com.google.firebase.database.DataSnapshot

class TeamMapper {

    private fun mapToTeam(team: DataSnapshot): Team {
        return Team(
            team.child("TeamName").value as String?,
            team.key,
            team.child("BadgeURL").value as String?,
            team.child("StadiumName").value as String?,
        )
    }
}
