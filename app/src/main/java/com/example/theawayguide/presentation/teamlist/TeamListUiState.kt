package com.example.theawayguide.presentation.teamlist

import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team

data class TeamListUiState(
    var teamList: List<Team> = emptyList(),
    var leagueList: List<League> = emptyList(),
    var selectedLeague: String = "All Teams"
)
