package com.example.theawayguide.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.theawayguide.R

@Preview
@Composable
fun SplashScreenComposable() {
    Surface(
        color = MaterialTheme.colors.primary,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painterResource(id = R.drawable.ic_baseline_sports_football_24),
                stringResource(R.string.app_logo_description),
                modifier = Modifier.size(128.dp),
                tint = MaterialTheme.colors.onPrimary
            )
            Text(
                stringResource(R.string.app_name),
                style = MaterialTheme.typography.h3,
                modifier = Modifier.padding(top = 8.dp),
                color = MaterialTheme.colors.onPrimary
            )
        }
    }
}
