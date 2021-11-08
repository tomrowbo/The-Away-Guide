package com.example.theawayguide.presentation.teamlist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import coil.compose.rememberImagePainter
import com.example.theawayguide.ui.theme.TheAwayGuideTheme
import com.google.firebase.database.FirebaseDatabase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class TeamListFragment : Fragment() {

    companion object {
        fun newInstance() = TeamListFragment()
    }

    val viewModel: TeamListViewModel by viewModels()

    // val activityViewModel: TeamListViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                TheAwayGuideTheme {
                    ListTeamsComposable()
                }
            }
        }
    }

    @Composable
    fun ListTeamsComposable() {
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
                        title = { Text(text = "All Football Teams") },
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

    @Composable
    private
    fun ContentComposable() {
        Column(Modifier.fillMaxWidth()) {
            TeamCard()
            TeamCard()
            TeamCard()
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
    fun TeamCard() {
        Card(
            Modifier
                .fillMaxWidth()
                .padding(vertical = 1.dp)
        ) {
            Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = rememberImagePainter("https://upload.wikimedia.org/wikipedia/en/thumb/7/7a/Manchester_United_FC_crest.svg/1200px-Manchester_United_FC_crest.svg.png"),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(16.dp)
                            .size(32.dp),
                        contentScale = ContentScale.Crop
                    )
                    Column {
                        println("HERE 1234")
                        val database = FirebaseDatabase.getInstance().getReference("Teams").get().addOnSuccessListener {
                            if (it.exists()) {
                                println("Team Name" + it.child("ManUtd").child("TeamName").value)
                            } else {
                                println("Error")
                            }
                        }.addOnFailureListener {
                            println("Error")
                        }
//
                        Text(text = "Manchester United", style = MaterialTheme.typography.subtitle1)
                        Text(text = "Old Trafford")
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

    @Preview
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
}

data class NavDrawerItem(var route: String, var icon: ImageVector, var title: String)
