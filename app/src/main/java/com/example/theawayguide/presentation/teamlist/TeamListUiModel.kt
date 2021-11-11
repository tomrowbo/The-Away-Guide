package com.example.theawayguide.presentation.teamlist

import com.example.theawayguide.domain.Team

data class TeamListUiModel(
    var teamList: List<Team> = emptyList(),
    var loading: Boolean = false
)
