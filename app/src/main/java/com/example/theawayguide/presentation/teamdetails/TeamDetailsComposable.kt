package com.example.theawayguide.presentation.teamdetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.theawayguide.domain.Team

object TeamDetailsComposable {

    @Composable
    fun TeamDetailsScreen(viewModel: TeamDetailsViewModel) {
        val uiModel = viewModel.uiModel
        val team = uiModel.value.team
        val isLoading = viewModel.loadingState.value
        if (isLoading) {
            LoadingComposable()
        } else {
            Surface(color = MaterialTheme.colors.background) {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text(text = team.name ?: "Team Name") },
                        )
                    }
                ) {
                    ContentComposable(team)
                }
            }
        }
    }

    @Composable
    fun ContentComposable(team: Team) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {

            Image(
                painter = rememberImagePainter(team.imageUrl),
                contentDescription = "Stadium Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.777f),
                contentScale = ContentScale.Crop
            )

            Column(Modifier.padding(horizontal = 8.dp)) {
                Text(team.stadiumName ?: "Stadium Name", style = MaterialTheme.typography.h2)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Place,
                        contentDescription = "Map Icon",
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                    )
                    // TODO: Get address from google maps location??
                    Text(
                        "Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA",
                        style = MaterialTheme.typography.body1
                    )
                }
                Text(team.description ?: "Description")
            }
        }
    }

    // TODO: Make a common component class so this doesnt need to be repeated
    @Composable
    fun LoadingComposable() {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(Modifier.size(64.dp))
            Text("Loading...", style = MaterialTheme.typography.h3, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
