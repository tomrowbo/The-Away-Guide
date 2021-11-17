package com.example.theawayguide.presentation.teamdetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberImagePainter
import com.example.theawayguide.domain.Team
import com.example.theawayguide.presentation.utils.rememberMapViewWithLifecycle
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap

object TeamDetailsComposable {

    @Composable
    fun TeamDetailsScreen(viewModel: TeamDetailsViewModel) {
        val uiModel = viewModel.uiModel
        Surface(color = MaterialTheme.colors.background) {
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
    }

    @Composable
    fun ContentComposable(team: Team) {
        var tabIndex by remember { mutableStateOf(0) }
        val tabData = listOf(
            "OVERVIEW",
            "LOCATION",
            "PUBS",
            "RESTAURANTS",
            "HOTELS"
        )
        Column {
            ScrollableTabRow(selectedTabIndex = tabIndex, edgePadding = 0.dp) {
                tabData.forEachIndexed { index, text ->
                    Tab(selected = tabIndex == index, onClick = {
                        tabIndex = index
                    }, text = {
                        Text(text = text)
                    })
                }
            }
            when (tabIndex) {
                0 -> OverviewTab(team.imageUrl, team.stadiumName, team.description)
                1 -> LocationTab()
            }
        }
    }

    // TODO: Make a common component class so this doesn't need to be repeated
    @Composable
    fun LoadingComposable() {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CircularProgressIndicator(Modifier.size(64.dp))
            Text(
                "Loading...",
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(top = 8.dp)
            )
        }
    }

    @Composable
    fun OverviewTab(imageUrl: String?, stadiumName: String?, description: String?) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colors.surface)
        ) {
            Image(
                painter = rememberImagePainter(imageUrl),
                contentDescription = "Stadium Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .aspectRatio(1.777f),
                contentScale = ContentScale.Crop
            )

            Column(
                Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stadiumName ?: "Stadium Name", style = MaterialTheme.typography.h2)
                Text(description ?: "Description")
            }
        }
    }

    @Composable
    fun LocationTab() {
        Column {
            MapViewComposable("53.46332134405343", "-2.2914572626499896")
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
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

        }
    }

    @Composable
    private fun MapViewComposable(latitude: String, longitude: String) {
        // The MapView lifecycle is handled by this composable. As the MapView also needs to be updated
        // with input from Compose UI, those updates are encapsulated into the MapViewContainer
        // composable. In this way, when an update to the MapView happens, this composable won't
        // recompose and the MapView won't need to be recreated. (Google Compose CraneSample)
        val mapView = rememberMapViewWithLifecycle()
        MapViewContainer(mapView, latitude, longitude)
    }

    @Composable
    private fun MapViewContainer(
        map: MapView,
        latitude: String,
        longitude: String
    ) {
        val cameraPosition = remember(latitude, longitude) {
            LatLng(latitude.toDouble(), longitude.toDouble())
        }

        LaunchedEffect(map) {
            val googleMap = map.awaitMap()
            googleMap.addMarker { position(cameraPosition) }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, Zoom))
        }
        AndroidView({ map })
        }

}

private const val Zoom = 15f
