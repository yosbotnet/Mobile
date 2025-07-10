package edu.unibo.tracker.commonItem

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun TopBarSec(string: String,navController: NavController){
    TopAppBar(
        title = { Text(string, color = MaterialTheme.colors.onPrimary) },
        backgroundColor = MaterialTheme.colors.primary,
        elevation = 4.dp,
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("home")
            }) {
                Icon(Icons.Filled.ArrowBack, contentDescription = null)
            }
        })
}