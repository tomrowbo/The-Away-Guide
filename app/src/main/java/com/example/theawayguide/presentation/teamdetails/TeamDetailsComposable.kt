package com.example.theawayguide.presentation.teamdetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Place
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.theawayguide.R
import com.example.theawayguide.domain.Team
import com.example.theawayguide.presentation.common.ErrorComposable
import com.example.theawayguide.presentation.common.LoadingComposable
import com.example.theawayguide.presentation.common.RatingComposable
import com.example.theawayguide.presentation.utils.rememberMapViewWithLifecycle
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.MapView
import com.google.android.libraries.maps.model.LatLng
import com.google.maps.android.ktx.addMarker
import com.google.maps.android.ktx.awaitMap

object TeamDetailsComposable {

    @ExperimentalMaterialApi
    @Composable
    fun TeamDetailsScreen(viewModel: TeamDetailsViewModel, navController: NavController) {
        val uiModel = viewModel.uiState
        Surface(color = MaterialTheme.colors.background) {
            val team = uiModel.value.team
            val pubList = uiModel.value.pubList
            val restaurantList = uiModel.value.restaurantList
            val hotelList = uiModel.value.hotelList
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = {
                            Text(
                                text = team?.name
                                    ?: stringResource(R.string.app_name)
                            )
                        },
                    )
                }
            ) {
                val isLoading = viewModel.loadingState.value
                if (isLoading) {
                    LoadingComposable()
                } else {
                    if (team != null) {
                        ContentComposable(team, pubList, restaurantList, hotelList, navController)
                    } else
                        ErrorComposable(errorMsg = "Team not found")
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable
    fun ContentComposable(
        team: Team,
        pubList: List<AttractionSummaryUiState>?,
        restaurantList: List<AttractionSummaryUiState>?,
        hotelList: List<AttractionSummaryUiState>?,
        navController: NavController
    ) {
        var tabIndex by remember { mutableStateOf(0) }
        val tabData = listOf(
            stringResource(R.string.overview_tab_text),
            stringResource(R.string.location_tab_text),
            stringResource(R.string.pubs_tab_text),
            stringResource(R.string.restaurant_tab_text),
            stringResource(R.string.hotel_tab_text)
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
                1 -> LocationTab(team.mapsLatitude, team.mapsLongitude)
                2 -> PubsTab(pubList, navController)
                3 -> RestaurantsTab(restaurantList, navController)
                4 -> HotelsTab(hotelList, navController)
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable private
    fun HotelsTab(hotelList: List<AttractionSummaryUiState>?, navController: NavController) {
        LazyColumn {
            if (hotelList != null) {
                items(hotelList) { hotel ->
                    AttractionSummaryComposable(
                        name = hotel.name,
                        imageUrl = hotel.imageUrl,
                        icons = hotel.ratings,
                        ratingAmount = hotel.totalRatings,
                        shortAddress = hotel.shortAddress,
                        navController = navController,
                        placeId = hotel.placeId
                    )
                }
            } else {
                item {
                    ErrorComposable(stringResource(R.string.could_not_load_text))
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable private
    fun RestaurantsTab(
        restaurantList: List<AttractionSummaryUiState>?,
        navController: NavController
    ) {
        LazyColumn {
            if (restaurantList != null) {
                items(restaurantList) { restaurant ->
                    AttractionSummaryComposable(
                        name = restaurant.name,
                        imageUrl = restaurant.imageUrl,
                        icons = restaurant.ratings,
                        ratingAmount = restaurant.totalRatings,
                        shortAddress = restaurant.shortAddress,
                        navController = navController,
                        placeId = restaurant.placeId
                    )
                }
            } else {
                item {
                    ErrorComposable(stringResource(R.string.could_not_load_text))
                }
            }
        }
    }

    @ExperimentalMaterialApi
    @Composable private
    fun PubsTab(pubList: List<AttractionSummaryUiState>?, navController: NavController) {
        LazyColumn {
            if (pubList != null) {
                items(pubList) { pub ->
                    AttractionSummaryComposable(
                        name = pub.name,
                        imageUrl = pub.imageUrl,
                        icons = pub.ratings,
                        ratingAmount = pub.totalRatings,
                        shortAddress = pub.shortAddress,
                        navController = navController,
                        placeId = pub.placeId
                    )
                }
            } else {
                item {
                    ErrorComposable(stringResource(R.string.could_not_load_text))
                }
            }
        }
    }

    @Composable
    fun OverviewTab(imageUrl: String?, stadiumName: String?, description: String?) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
        ) {
            Column(Modifier.background(MaterialTheme.colors.surface)) {
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = "Stadium Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.777f),
                    contentScale = ContentScale.Crop
                )
                Text(stadiumName ?: "Stadium Name", style = MaterialTheme.typography.h2, modifier = Modifier.padding(horizontal = 8.dp))
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(Modifier.background(MaterialTheme.colors.surface).padding(horizontal = 8.dp)) {
                Text("Ground Description", style = MaterialTheme.typography.subtitle1)
                Text(description ?: "Description", style = MaterialTheme.typography.body1)
            }
        }
    }

    @Composable
    fun LocationTab(mapsLatitude: Double?, mapsLongitude: Double?) {
        Column {
            if (mapsLatitude != null && mapsLongitude != null) {
                MapViewComposable(mapsLatitude, mapsLongitude)
            }
            Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
                Icon(
                    Icons.Filled.Place,
                    contentDescription = "Map Icon",
                    modifier = Modifier
                        .height(35.dp)
                        .width(35.dp)
                )
                // TODO: Get address from google maps location??
//                Text(
//                    "Sir Matt Busby Way, Old Trafford, Stretford, Manchester M16 0RA",
//                    style = MaterialTheme.typography.body1
//                )
            }
        }
    }

    @Composable
    private fun MapViewComposable(latitude: Double, longitude: Double) {
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
        latitude: Double,
        longitude: Double
    ) {
        val cameraPosition = remember(latitude, longitude) {
            LatLng(latitude, longitude)
        }

        LaunchedEffect(map) {
            val googleMap = map.awaitMap()
            googleMap.addMarker { position(cameraPosition) }
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cameraPosition, Zoom))
        }
        AndroidView({ map })
    }

    @ExperimentalMaterialApi
    @Composable
    fun AttractionSummaryComposable(name: String?, imageUrl: String?, icons: List<ImageVector>?, ratingAmount: Int?, shortAddress: String?, placeId: String?, navController: NavController) {
        Card(
            modifier = Modifier.padding(4.dp),
            onClick = { navController.navigate("attraction/$placeId") }
        ) {
            Column {
                Image(
                    painter = rememberImagePainter(imageUrl),
                    contentDescription = "Attraction Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(1.85f),
                    contentScale = ContentScale.Crop
                )
                Column(Modifier.padding(4.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            name ?: "Attraction Name",
                            style = MaterialTheme.typography.subtitle1,
                            modifier = Modifier.weight(1f, fill = false)
                        )
                        icons?.let {
                            RatingComposable(icons, ratingAmount)
                        }
                    }
                    Text(
                        text = shortAddress ?: "",
                        style = MaterialTheme.typography.body1,
                    )
                }
            }
        }
    }
}

private const val Zoom = 15f
