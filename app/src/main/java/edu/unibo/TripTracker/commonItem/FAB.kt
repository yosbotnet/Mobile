package edu.unibo.tracker.commonItem

import androidx.compose.material.FloatingActionButton
import androidx.compose.material.FloatingActionButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

import edu.unibo.tracker.R

@Composable
fun FloatingActionButtons(navController: NavController){
    val fabNav = "permission"

    FloatingActionButton(
        onClick = { navController.navigate(fabNav) {
        navController.graph.startDestinationRoute?.let { route ->
            popUpTo(route) {
                saveState = true
            }
        }
        // Avoid multiple copies of the same destination when
        // reselecting the same item
        launchSingleTop = true
        // Restore state when reselecting a previously selected item
        restoreState = true
    }},
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    ) {
        Icon(painterResource(id = R.drawable.ic_pin), "pin")
    }
}