package com.example.theawayguide.presentation.teamdetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.theawayguide.domain.Team
import com.example.theawayguide.presentation.teamlist.NavDrawerItem
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

object TeamDetailsComposable {

    @Composable
    fun TeamDetailsScreen(viewModel: TeamDetailsViewModel) {
        val uiModel = viewModel.uiModel
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
                        title = { Text(text = "Manchester United") },
                        navigationIcon = {
                            IconButton(
                                onClick = {
                                    openDrawer(scope, scaffoldState)
                                }
                            ) {
                                Icon(Icons.Filled.Menu, contentDescription = "Menu Button")
                            }
                        }
                    )
                }
            ) {
                ContentComposable()
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
    fun ContentComposable() {
        val scrollState = rememberScrollState()
        Column(Modifier.fillMaxSize()) {
            Image(
                painter = rememberImagePainter("https://www.stadiumjourney.com/wp-content/uploads/2016/11/Screen-Shot-2019-10-17-at-6.50.55-PM.png"),
                contentDescription = "Stadium Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.4f),
                contentScale = ContentScale.Crop
            )
            Column(Modifier.padding(horizontal = 8.dp)) {
                Text("Old Trafford", style = MaterialTheme.typography.h2)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        Icons.Filled.Place,
                        contentDescription = "Map Icon",
                        modifier = Modifier
                            .height(35.dp)
                            .width(35.dp)
                    )
                    Text(
                        "Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA",
                        style = MaterialTheme.typography.body1
                    )
                }
                Text("Old Trafford is a football stadium in Old Trafford, Greater Manchester, England, and the home of Manchester United. With a capacity of 74,140 seats, it is the largest club football stadium (and second-largest football stadium overall after Wembley Stadium) in the United Kingdom, and the eleventh-largest in Europe. It is about 0.5 miles (800 m) from Old Trafford Cricket Ground and the adjacent tram stop. (Wikipedia)")
            }
        }

        }

    //TODO: We don't need a nav drawer in this - just a back button
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
                    "All Football Teams",
                    Icons.Filled.Menu,
                    "All Football Teams"
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
                NavDrawerItem("Settings", Icons.Filled.Settings, "Settings"),
                selected = false
            ) {}
        }
    }

    @Composable
    fun LoadingComposable(){
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            CircularProgressIndicator(Modifier.size(64.dp))
            Text("Loading...", style = MaterialTheme.typography.h3, modifier = Modifier.padding(top=8.dp))
        }
    }
}