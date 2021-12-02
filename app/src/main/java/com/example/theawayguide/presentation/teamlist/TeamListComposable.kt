package com.example.theawayguide.presentation.teamlist

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.theawayguide.R
import com.example.theawayguide.domain.League
import com.example.theawayguide.domain.Team
import com.example.theawayguide.presentation.common.SplashScreenComposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
object TeamListComposable {

    @Composable
    fun TeamListScreen(viewModel: TeamListViewModel, navController: NavController) {
        val uiModel = viewModel.uiState
        val isLoading = viewModel.loadingState.value
        if (isLoading) {
            SplashScreenComposable()
        } else {
            Surface(color = MaterialTheme.colors.background) {
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                Scaffold(
                    scaffoldState = scaffoldState,
                    drawerContent = {
                        DrawerComposable(uiModel.value.leagueList, viewModel, uiModel.value.selectedLeague)
                    },
                    topBar = {
                        TopAppBar(
                            title = { Text(text = uiModel.value.selectedLeague) },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        toggleDrawer(scope, scaffoldState)
                                    }
                                ) {
                                    Icon(
                                        Icons.Filled.Menu,
                                        contentDescription = stringResource(R.string.menu_button_description)
                                    )
                                }
                            }
                        )
                    }
                ) {
                    ContentComposable(uiModel, navController)
                }
            }
        }
    }

    @Composable
    private
    fun ContentComposable(
        uiState: MutableState<TeamListUiState>,
        navController: NavController
    ) {

        val teams = uiState.value.teamList
        LazyColumn(Modifier.fillMaxSize()) {
            items(teams) { team ->
                TeamCard(team, navController)
            }
        }
    }

    private fun toggleDrawer(scope: CoroutineScope, scaffoldState: ScaffoldState) {
        scope.launch {
            scaffoldState.drawerState.apply {
                if (isClosed) open() else close()
            }
        }
    }

    @Composable
    fun TeamCard(team: Team, navController: NavController) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp),
            onClick = { team.url?.let { navController.navigate("teamDetails/${team.url}") } },
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberImagePainter(team.badgeUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(32.dp),
                        contentScale = ContentScale.Fit
                    )
                    Column {
                        Text(
                            text = team.name ?: stringResource(R.string.team_name_placeholder_text),
                            style = MaterialTheme.typography.subtitle1
                        )
                        Text(
                            text = team.stadiumName
                                ?: stringResource(R.string.stadium_name_placeholder_text)
                        )
                    }
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
}
