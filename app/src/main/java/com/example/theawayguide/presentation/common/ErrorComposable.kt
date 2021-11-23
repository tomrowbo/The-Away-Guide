package com.example.theawayguide.presentation.common

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.SentimentDissatisfied
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComposable(errorMsg: String) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(Icons.Outlined.SentimentDissatisfied, "Sad face", Modifier.size(64.dp))
        Text(
            errorMsg,
            style = MaterialTheme.typography.h3,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}
