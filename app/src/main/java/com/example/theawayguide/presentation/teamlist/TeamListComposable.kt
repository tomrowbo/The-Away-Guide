package com.example.theawayguide.presentation.teamlist

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.theawayguide.R
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
                        DrawerComposable()
                    },
                    topBar = {
                        TopAppBar(
                            title = { Text(text = stringResource(R.string.all_teams_title_text)) },
                            navigationIcon = {
                                IconButton(
                                    onClick = {
                                        openDrawer(scope, scaffoldState)
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

    private fun openDrawer(scope: CoroutineScope, scaffoldState: ScaffoldState) {
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
    fun NavItemCard(item: NavDrawerItem, selected: Boolean, onItemClick: () -> Unit) {
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
                imageVector = item.icon,
                contentDescription = item.title,
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .height(35.dp)
                    .width(35.dp)
            )
            Text(
                text = item.title,
                modifier = Modifier.padding(start = 8.dp)
            )
        }
    }

    @Composable
    fun DrawerComposable() {
        Column {
            Surface(modifier = Modifier.fillMaxWidth(), color = MaterialTheme.colors.primary) {
                Text(
                    "The Away Guide",
                    style = MaterialTheme.typography.h1,
                    color = MaterialTheme.colors.onPrimary,
                    modifier = Modifier.padding(bottom = 32.dp, top = 64.dp, start = 16.dp)
                )
            }
            NavItemCard(
                NavDrawerItem(
                    stringResource(R.string.all_teams_title_text),
                    Icons.Filled.Menu,
                    stringResource(R.string.all_teams_title_text)
                ),
                selected = false
            ) {}
            NavItemCard(
                NavDrawerItem(
                    "Premier League Teams",
                    Icons.Filled.Menu,
                    "Premier League Teams"
                ),
                selected = false
            ) {}
            NavItemCard(
                NavDrawerItem(
                    "Championship Teams",
                    Icons.Filled.Menu,
                    "Championship Teams"
                ),
                selected = false
            ) {}
            NavItemCard(
                NavDrawerItem(
                    "League One Teams",
                    Icons.Filled.Menu,
                    "League One Teams"
                ),
                selected = true
            ) {}
            NavItemCard(
                NavDrawerItem(
                    "League Two Teams",
                    Icons.Filled.Menu,
                    "League Two Teams"
                ),
                selected = false
            ) {}
            NavItemCard(
                NavDrawerItem(
                    stringResource(R.string.settings_title_text),
                    Icons.Filled.Settings,
                    stringResource(R.string.settings_title_text)
                ),
                selected = false
            ) {}
        }
    }
}
data class NavDrawerItem(var route: String, var icon: ImageVector, var title: String)
