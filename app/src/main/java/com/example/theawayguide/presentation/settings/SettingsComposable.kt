package com.example.theawayguide.presentation.settings

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.theawayguide.R
import com.example.theawayguide.domain.League
import com.example.theawayguide.presentation.teamlist.TeamListViewModel

@ExperimentalMaterialApi
object SettingsComposable {

    @Composable
    fun SettingsScreen(viewModel: SettingsViewModel) {
        Surface(color = MaterialTheme.colors.background) {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text(text = "Settings") }
                    )
                }
            ) {
                ContentComposable(viewModel)
            }
        }
    }

    @Composable
    private
    fun ContentComposable(
        viewModel: SettingsViewModel
    ) {
        val uiState = viewModel.uiState
        val radius = uiState.value.radiusInKm
        val sliderProportion = uiState.value.sliderProportion
        RadiusCard(viewModel, radius, sliderProportion)
    }
}

@Composable
fun RadiusCard(viewModel: SettingsViewModel, radius: Double, sliderProportion: Float) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 1.dp),
    ) {
        Column(Modifier.padding(16.dp)) {
            Column {
                Text(
                    text = "Distance",
                    style = MaterialTheme.typography.subtitle1.copy(fontWeight = FontWeight.Bold)
                )
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "${radius}km",
                        style = MaterialTheme.typography.subtitle1,
                        modifier = Modifier.padding(end = 4.dp)
                    )
                    Slider(sliderProportion, { viewModel.onSliderChanged(it) })
                }
                Text(
                    text = "The maximum distance attractions will show from the stadium"
                )
            }
        }
    }
}

@Composable
fun NavItemCard(painter: Painter, title: String, selected: Boolean, onItemClick: () -> Unit) {
    val background =
        if (selected) MaterialTheme.colors.primaryVariant else MaterialTheme.colors.surface
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(bottom = 1.dp)
            .fillMaxWidth()
            .clickable(onClick = { onItemClick() })
            .height(45.dp)
            .background(background)
            .padding(start = 10.dp)
    ) {
        Image(
            painter = painter,
            contentDescription = stringResource(R.string.leagueBadgeDescription),
            contentScale = ContentScale.Fit,
            modifier = Modifier
                .height(35.dp)
                .width(35.dp)
                .background(Color.White)
                .border(BorderStroke(1.dp, Color.Black))
        )
        Text(
            text = title,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}

@Composable
fun DrawerComposable(
    leagueList: List<League>,
    viewModel: TeamListViewModel,
    selectedLeague: String
) {
    Column {
        Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.primary) {
            Text(
                stringResource(id = R.string.app_name),
                style = MaterialTheme.typography.h1,
                color = MaterialTheme.colors.onPrimary,
                modifier = Modifier.padding(bottom = 32.dp, top = 64.dp, start = 16.dp)
            )
        }
        LazyColumn {
            item {
                NavItemCard(
                    painterResource(R.drawable.ic_baseline_format_list_bulleted_24),
                    stringResource(R.string.all_teams_title_text),
                    selected = (selectedLeague == "All Teams")
                ) {
                    viewModel.onAllTeamsClicked()
                }
            }
            items(leagueList) { league ->
                if (league.id != null && league.name != null)
                    NavItemCard(
                        rememberImagePainter(league.badgeUrl),
                        league.name,
                        selected = (selectedLeague == league.name)
                    ) {
                        viewModel.onLeagueClicked(league.id, league.name)
                    }
            }
        }
    }
}
