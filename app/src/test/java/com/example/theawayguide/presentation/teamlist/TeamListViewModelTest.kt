package com.example.theawayguide.presentation.teamlist

import androidx.compose.runtime.setValue
import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team
import com.example.theawayguide.repository.TeamRepositoryImpl
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import java.util.*

class TeamListViewModelTest {
    @MockK
    private lateinit var teamRepositoryImpl: TeamRepositoryImpl

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
    }

    @Test
    fun WHEN_init_THEN_returnScreenInfo() {
        runBlocking {
            // GIVEN
            coEvery { teamRepositoryImpl.retrieveTeams() } returns listOf(Team())
            coEvery { teamRepositoryImpl.getLeagues() } returns listOf(League())

            // WHEN
            val teamListViewModel = getViewModel() // Will init here

            // THEN
            assertEquals(teamListViewModel.uiState.value.leagueList, listOf(League()))
            assertEquals(teamListViewModel.uiState.value.teamList, listOf(Team()))
            assertEquals(teamListViewModel.loadingState.value, false)
            assertEquals(teamListViewModel.errorState.value, false)
        }
    }

    @Test
    fun WHEN_init_AND_error_THEN_returnScreenInfo() {
        runBlocking {
            // GIVEN
            coEvery { teamRepositoryImpl.retrieveTeams() } returns listOf(Team())
            coEvery { teamRepositoryImpl.getLeagues() } throws Exception()

            // WHEN
            val teamListViewModel = getViewModel() // Will init here

            // THEN
            assertEquals(teamListViewModel.loadingState.value, false)
            assertEquals(teamListViewModel.errorState.value, true)
        }
    }

    private fun getViewModel(): TeamListViewModel {
        return TeamListViewModel(teamRepositoryImpl)
    }
}
