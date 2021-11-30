package com.example.theawayguide.presentation.attractiondetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.theawayguide.presentation.common.RatingComposable

object AttractionDetailsComposable {

    @Composable
    fun AttractionScreen(attractionDetailsViewModel: AttractionDetailsViewModel, navController: NavController) {
        val uiState = attractionDetailsViewModel.uiState.value
        val isLoading = attractionDetailsViewModel.loadingState

        ContentComposable(uiState, navController)
    }

    @Composable
    private fun ContentComposable(uiState: AttractionDetailsUiState, navController: NavController) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colors.background)
        ) {

            Column(Modifier.background(MaterialTheme.colors.surface)) {
                HeaderImage(uiState.imageUrl)
                Column(
                    Modifier.padding(horizontal = 8.dp),
                ) {
                    Text(uiState.name ?: "Attraction Name", style = MaterialTheme.typography.h2)
                    RatingAndPriceRow(uiState.ratingIcons, uiState.totalRatings, uiState.priceLevel)
                }
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(8.dp),
            ) {
                PhoneNumberRow(uiState.phoneNumber)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(8.dp),
            ) {
                AddressRow(uiState.address)
            }
            Spacer(modifier = Modifier.height(4.dp))
            Column(
                Modifier.fillMaxWidth().background(MaterialTheme.colors.surface).padding(8.dp),
            ) {
                OpeningHoursComposable(uiState.openingHours)
            }
        }
    }

    @Composable
    private fun OpeningHoursComposable(openingHours: List<String>?) {
        Column() {
            Text("Opening Hours", style = MaterialTheme.typography.subtitle1)
            openingHours?.forEach { dayInfo ->
                Text(dayInfo, style = MaterialTheme.typography.body1)
            }
        }
    }

    @Composable
    private fun AddressRow(address: String?) {
        if (address != null) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Map,
                    "Map Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
                Text(address, style = MaterialTheme.typography.body1)
            }
        }
    }

    @Composable
    private fun PhoneNumberRow(phoneNumber: String?) {
        phoneNumber?.let {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    Icons.Filled.Phone,
                    "Phone Icon",
                    modifier = Modifier
                        .size(24.dp)
                        .padding(end = 4.dp)
                )
                Text(it, style = MaterialTheme.typography.body1)
            }
        }
    }

    @Composable
    private fun RatingAndPriceRow(ratingIcons: List<ImageVector>?, totalRatings: Int?, priceLevel: String?) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            ratingIcons?.let { RatingComposable(icons = it, ratingAmount = totalRatings) }
            Text(
                priceLevel ?: "",
                color = MaterialTheme.colors.primary,
                style = MaterialTheme.typography.body1
            )
        }
    }

    @Composable
    private fun HeaderImage(imageUrl: String?) {
        Image(
            painter = rememberImagePainter(imageUrl),
            contentDescription = "Attraction Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.777f),
            contentScale = ContentScale.Crop
        )
    }
}
