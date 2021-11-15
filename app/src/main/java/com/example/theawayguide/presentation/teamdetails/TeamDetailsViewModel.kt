package com.example.theawayguide.presentation.teamdetails

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
class TeamDetailsViewModel
@Inject
constructor(
    private val teamRepository: TeamRepository
) : ViewModel() {

    var uiModel: MutableState<TeamDetailsUiModel> = mutableStateOf(TeamDetailsUiModel())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    init {
        getTeamDetails()
    }

    private fun getTeamDetails() {

        viewModelScope.launch {
            loadingState.value = true
            //TODO: Get team details
            loadingState.value = false
        }
    }
}
