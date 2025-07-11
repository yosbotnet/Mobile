package edu.unibo.tracker.commonItem

import androidx.compose.material.*
import androidx.compose.runtime.*
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

    // Track if we just requested permissions
    var justRequestedPermissions by remember { mutableStateOf(false) }

    // Navigate to maps when permissions are granted AND we just requested them
    LaunchedEffect(locationPermissionsState.allPermissionsGranted, justRequestedPermissions) {
        if (locationPermissionsState.allPermissionsGranted && justRequestedPermissions) {
            justRequestedPermissions = false // Reset the flag
            kotlinx.coroutines.delay(100) // Small delay for stability
            navController.navigate("maps") {
                navController.graph.startDestinationRoute?.let { route ->
                    popUpTo(route) {
                        saveState = true
                    }
                }
                restoreState = true
            }
        }
    }

    FloatingActionButton(
        onClick = {
            if (locationPermissionsState.allPermissionsGranted) {
                // Permissions already granted, navigate immediately
                navController.navigate("maps") {
                    navController.graph.startDestinationRoute?.let { route ->
                        popUpTo(route) {
                            saveState = true
                        }
                    }
                    restoreState = true
                }
            } else {
                // Request permissions and set flag
                justRequestedPermissions = true
                locationPermissionsState.launchMultiplePermissionRequest()
            }
        },
        backgroundColor = MaterialTheme.colors.secondary,
        elevation = FloatingActionButtonDefaults.elevation(8.dp)
    ) {
        Icon(painterResource(id = R.drawable.ic_pin), "pin")
    }
}