package com.example.theawayguide.presentation

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.theawayguide.presentation.common.RatingComposable

@Preview(showSystemUi = true)
@Composable
fun AttractionScreen(){
    val scrollState = rememberScrollState()
    Column(
        Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colors.surface)
    ) {
        Image(
            painter = rememberImagePainter(""),
            contentDescription = "Stadium Image",
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1.777f),
            contentScale = ContentScale.Crop
        )

        Column(
            Modifier.padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text("Attraction Name", style = MaterialTheme.typography.h2)
            Row(Modifier.horizontalScroll(scrollState)) {
                TagComposable("American")
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                //RatingComposable(icons = emptyList(), ratingAmount = )
                Text("****")
                Text("££", color = MaterialTheme.colors.primary, style = MaterialTheme.typography.body2)
            }
            Row(verticalAlignment = Alignment.CenterVertically){
                Icon(Icons.Filled.Phone, "Phone Icon", modifier = Modifier.size(16.dp).padding(end = 4.dp))
                Text("+44999334235529")
            }
            Text("Opening Hours", style = MaterialTheme.typography.subtitle1)
            Text("Monday: 9am-5pm", style = MaterialTheme.typography.subtitle1)
            Text("Tuesday: 9am-5pm", style = MaterialTheme.typography.subtitle1)
            Text("Wednesday: 9am-5pm", style = MaterialTheme.typography.subtitle1)
            Text("Thursday: 9am-5pm", style = MaterialTheme.typography.subtitle1)
            Text("Friday: 9am-5pm", style = MaterialTheme.typography.subtitle1)

        }
    }
}

@Composable
fun TagComposable(tagName: String) {
    Card(
        backgroundColor = MaterialTheme.colors.primaryVariant,
        modifier = Modifier.padding(4.dp)
    ) {
        Text(tagName, modifier = Modifier.padding(2.dp))
    }
}
