package com.example.theawayguide.presentation.teamlist

import android.util.Log
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

    var uiModel: MutableState<TeamListUiModel> = mutableStateOf(TeamListUiModel())

    var loadingState: MutableState<Boolean> = mutableStateOf(false)

    init {
            getTeams()
    }

    private fun getTeams() {

        viewModelScope.launch {
            loadingState.value = true
            delay(5000)
            uiModel.value = uiModel.value.copy().apply{teamList = teamRepository.getAll() ?: emptyList()}
            loadingState.value = false
        }
    }
}
