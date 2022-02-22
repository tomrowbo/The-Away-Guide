package com.example.theawayguide.presentation.common

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun RatingComposable(icons: List<ImageVector>, ratingAmount: Int?) {
    Row {
        Icon(
            imageVector = icons[0],
            contentDescription = "Star",
            Modifier.size(16.dp),
            tint = MaterialTheme.colors.onSurface
        )
        Icon(
            imageVector = icons[1],
            contentDescription = "Star",
            Modifier.size(16.dp),
            tint = MaterialTheme.colors.onSurface
        )
        Icon(
            imageVector = icons[2],
            contentDescription = "Star",
            Modifier.size(16.dp),
            tint = MaterialTheme.colors.onSurface
        )
        Icon(
            imageVector = icons[3],
            contentDescription = "Star",
            Modifier.size(16.dp),
            tint = MaterialTheme.colors.onSurface
        )
        Icon(
            imageVector = icons[4],
            contentDescription = "Star",
            Modifier.size(16.dp),
            tint = MaterialTheme.colors.onSurface
        )
        ratingAmount?.let {
            Text(
                text = "($ratingAmount)",
                style = MaterialTheme.typography.body2,
                modifier = Modifier.padding(start = 4.dp),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}
