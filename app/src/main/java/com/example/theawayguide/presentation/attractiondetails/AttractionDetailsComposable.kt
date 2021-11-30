package com.example.theawayguide.presentation.attractiondetails

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Map
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.example.theawayguide.R
import com.example.theawayguide.presentation.common.RatingComposable

object AttractionDetailsComposable {

    @Composable
    fun AttractionScreen(attractionDetailsViewModel: AttractionDetailsViewModel, navController: NavController) {
        val uiState = attractionDetailsViewModel.uiState.value
        val isLoading = attractionDetailsViewModel.loadingState

        ContentComposable(uiState)
    }

    @Composable
    private fun ContentComposable(uiState: AttractionDetailsUiState) {
        val scrollState = rememberScrollState()
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState)
                .background(MaterialTheme.colors.surface)
        ) {

            HeaderImage(uiState.imageUrl)

            Column(
                Modifier.padding(horizontal = 8.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Text(uiState.name?:"Team Name", style = MaterialTheme.typography.h2)
                AllTagsRow(uiState.tags, scrollState)
                RatingAndPriceRow(uiState.ratingIcons, uiState.totalRatings, uiState.priceLevel)
                PhoneNumberRow(uiState.phoneNumber)
                AddressRow(uiState.address)
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

    @Composable
    private fun AllTagsRow(tags: List<String>?, scrollState: ScrollState) {
        tags?.let {
            LazyRow() {
                items(it) { tag ->
                    TagComposable(tag)
                }
            }
        }
    }

    @Composable
    fun TagComposable(tagName: String) {
        Card(
            backgroundColor = MaterialTheme.colors.primary,
            modifier = Modifier.padding(4.dp)
        ) {
            Text(tagName, modifier = Modifier.padding(2.dp))
        }
    }
}
