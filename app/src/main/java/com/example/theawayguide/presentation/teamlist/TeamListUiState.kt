package com.example.theawayguide.presentation.teamlist

import androidx.compose.ui.res.stringResource
import com.example.theawayguide.R
import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team

data class TeamListUiState(
    var teamList: List<Team> = emptyList(),
    var leagueList: List<League> = emptyList(),
    var selectedLeague: String = "All Teams"
)
