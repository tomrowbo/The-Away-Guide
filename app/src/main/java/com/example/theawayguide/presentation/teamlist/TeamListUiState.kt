package com.example.theawayguide.presentation.teamlist

import com.example.theawayguide.domain.Team

data class TeamListUiState(
    var teamList: List<Team> = emptyList(),
)
