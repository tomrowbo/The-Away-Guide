package com.example.theawayguide.presentation.teamlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theawayguide.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TeamListViewModel
@Inject
constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {

    var uiState: MutableState<TeamListUiState> = mutableStateOf(TeamListUiState())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    var errorState: MutableState<Boolean> = mutableStateOf(false)

    init {
        getScreenInfo()
    }

    private fun getScreenInfo() {
        viewModelScope.launch {
            try {
                loadingState.value = true
                uiState.value = uiState.value.copy().apply {
                    teamList = teamRepository.retrieveTeams() ?: emptyList()
                    leagueList = teamRepository.getLeagues() ?: emptyList()
                }
                loadingState.value = false
            } catch (e: Exception) {
                errorState.value = true
                loadingState.value = false
            }
        }
    }

    fun retry() {
        errorState.value = false
        getScreenInfo()
    }

    fun onAllTeamsClicked() {
        uiState.value = uiState.value.copy().apply {
            teamList = teamRepository.getAllTeams()
            selectedLeague = "All Teams"
        }
    }

    fun onLeagueClicked(leagueId: String, leagueName: String) {
        uiState.value = uiState.value.copy().apply {
            teamList = teamRepository.getTeamsByLeague(leagueId)
            selectedLeague = leagueName
        }
    }
}
