package com.example.theawayguide.presentation.teamdetails

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.theawayguide.repository.TeamRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
class TeamDetailsViewModel
@Inject
constructor(
    private val teamRepository: TeamRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var uiModel: MutableState<TeamDetailsUiModel> = mutableStateOf(TeamDetailsUiModel())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    init {
        savedStateHandle.get<String>("teamUrl")?.let { teamUrl ->
            Log.d("DEBUGGING", teamUrl)
            getTeamDetails(teamUrl)
        }
    }

    private fun getTeamDetails(teamUrl: String) {
        viewModelScope.launch {
            loadingState.value = true
            uiModel.value = uiModel.value.copy().apply { team = teamRepository.getTeamDetails(teamUrl) }
            Log.d("DEBUGGING", uiModel.value.team.toString())
            loadingState.value = false
        }
    }
}
