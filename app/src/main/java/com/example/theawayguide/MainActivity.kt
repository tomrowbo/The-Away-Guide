package com.example.theawayguide

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.theawayguide.ui.theme.TheAwayGuideTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TheAwayGuideTheme {
                ListTeamsComposable()
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

    @Composable private
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
    fun DrawerComposable() {
        Text("Drawer Content")
    }
}

@Preview(showBackground = true)
@Composable
fun TeamCard() {
    Card(Modifier.fillMaxWidth().padding(vertical = 1.dp)) {
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
                    Text(text = "Manchester United", style = MaterialTheme.typography.subtitle1)
                    Text(text = "Old Trafford")
                }
            }
        }
    }
}
