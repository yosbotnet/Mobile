package edu.unibo.tracker.home.topBar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import edu.unibo.tracker.R
import edu.unibo.tracker.navigation.NavigationItem

@Composable
fun TopBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    TopAppBar(
        title = {
            Text(
                "FitTracker",
                color = MaterialTheme.colors.onPrimary,
                fontSize = 18.sp,
                modifier = Modifier.padding(3.dp)
            )
        },
        actions = {
            IconButton(
                onClick = { 
                    navController.navigate(NavigationItem.Profile.route)
                }
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_profile),
                    contentDescription = "Profile",
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        },
        elevation = 4.dp,
        backgroundColor = MaterialTheme.colors.primary
    )
}

