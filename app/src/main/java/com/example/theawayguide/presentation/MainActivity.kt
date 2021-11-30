package com.example.theawayguide.presentation

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.material.ExperimentalMaterialApi
import androidx.fragment.app.FragmentActivity
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.theawayguide.presentation.attractiondetails.AttractionDetailsComposable.AttractionScreen
import com.example.theawayguide.presentation.attractiondetails.AttractionDetailsViewModel
import com.example.theawayguide.presentation.teamdetails.TeamDetailsComposable.TeamDetailsScreen
import com.example.theawayguide.presentation.teamdetails.TeamDetailsViewModel
import com.example.theawayguide.presentation.teamlist.TeamListComposable.TeamListScreen
import com.example.theawayguide.presentation.teamlist.TeamListViewModel
import com.example.theawayguide.ui.theme.TheAwayGuideTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : FragmentActivity() {

    @ExperimentalMaterialApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            TheAwayGuideTheme {
                NavHost(navController = navController, "teamList") {
                    composable("teamList") {
                        val teamListViewModel = hiltViewModel<TeamListViewModel>()
                        TeamListScreen(teamListViewModel, navController)
                    }
                    composable("teamDetails/{teamUrl}") {
                        val teamDetailsViewModel = hiltViewModel<TeamDetailsViewModel>()
                        TeamDetailsScreen(
                            teamDetailsViewModel,
                            navController
                        )
                    }
                    composable("attraction/{attractionId}") {
                        val attractionDetailsViewModel = hiltViewModel<AttractionDetailsViewModel>()
                        AttractionScreen(attractionDetailsViewModel, navController)
                    }
                }
            }
        }
    }
}
