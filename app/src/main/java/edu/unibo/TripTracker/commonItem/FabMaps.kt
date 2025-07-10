package edu.unibo.tracker.commonItem

import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.unibo.tracker.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun FabMaps(navController: NavController) {

    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            android.Manifest.permission.ACCESS_COARSE_LOCATION,
            android.Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )
    FloatingActionButton(
        onClick = {
            if (locationPermissionsState.allPermissionsGranted) {
                navController.navigate("maps") {
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
                }
            } else {
                locationPermissionsState.launchMultiplePermissionRequest()
                if (locationPermissionsState.allPermissionsGranted) {
                    navController.navigate("maps")
                } else {
                    navController.navigate("home")
                }

            }
        },

        backgroundColor = MaterialTheme.colors.secondary,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    ) {
        Icon(painterResource(id = R.drawable.ic_pin), "pin")
    }
}



