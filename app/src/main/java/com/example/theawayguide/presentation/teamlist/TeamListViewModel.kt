package com.example.theawayguide.presentation.teamlist

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
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

    init {
        getTeams()
    }

    private fun getTeams() {

        viewModelScope.launch {
            loadingState.value = true
            uiState.value = uiState.value.copy().apply { teamList = teamRepository.getAll() ?: emptyList() }
            loadingState.value = false
        }
    }
}
