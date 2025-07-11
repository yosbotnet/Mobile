package edu.unibo.tracker.maps

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.location.Location
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import edu.unibo.tracker.commonItem.TopBarSec
import edu.unibo.tracker.Database.favPlaceList
import edu.unibo.tracker.R
import edu.unibo.tracker.login.user
import edu.unibo.tracker.userLocLatitude
import edu.unibo.tracker.userLocLongitude
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlinx.coroutines.launch
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

// Function to open gym website in browser
fun openGymWebsite(context: Context, websiteUrl: String?, gymName: String?) {
    if (websiteUrl.isNullOrBlank()) {
        Toast.makeText(context, "Website not available for $gymName", Toast.LENGTH_SHORT).show()
        return
    }
    
    try {
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(websiteUrl))
        Toast.makeText(context, "Opening $gymName website...", Toast.LENGTH_SHORT).show()
        context.startActivity(intent)
    } catch (e: Exception) {
        Log.e("MapsScreen", "Error opening website: ${e.message}")
        Toast.makeText(context, "Unable to open website", Toast.LENGTH_SHORT).show()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MapsScreen(navController: NavController) {
    Log.d("MapsScreen", "MapsScreen started")

    var isMapLoaded by remember {
        mutableStateOf(false)
    }

    var isLocationLoading by remember {
        mutableStateOf(false)
    }

    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    // Request location permissions
    val locationPermissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    )

    // Function to get current location
    fun getCurrentLocation() {
        isLocationLoading = true
        Log.d("MapsScreen", "=== STARTING LOCATION REQUEST ===")
        Log.d("MapsScreen", "Current values - lat: $userLocLatitude, lng: $userLocLongitude")
        Log.d("MapsScreen", "Permissions granted: ${locationPermissionsState.allPermissionsGranted}")

        coroutineScope.launch {
            try {
                val location = getCurrentLocationSimple(context)
                if (location != null) {
                    Log.d("MapsScreen", "✅ SUCCESS: Got location from GPS")
                    Log.d("MapsScreen", "New coordinates: ${location.latitude}, ${location.longitude}")
                    Log.d("MapsScreen", "Accuracy: ${location.accuracy}m, Time: ${location.time}")

                    // Update your global variables
                    userLocLatitude = location.latitude
                    userLocLongitude = location.longitude
                } else {
                    Log.w("MapsScreen", "❌ FAILED: No location available")
                    Log.w("MapsScreen", "Using fallback: 44.1484, 12.2359")
                    Log.w("MapsScreen", "Try opening Google Maps first to cache location")
                }
            } catch (e: Exception) {
                Log.e("MapsScreen", "💥 ERROR getting location: ${e.message}")
                e.printStackTrace()
            } finally {
                isLocationLoading = false
                Log.d("MapsScreen", "=== LOCATION REQUEST FINISHED ===")
            }
        }
    }

    // Check permissions and get location when the composable is first created
    LaunchedEffect(locationPermissionsState) {
        if (!locationPermissionsState.allPermissionsGranted) {
            locationPermissionsState.launchMultiplePermissionRequest()
        }
    }

    // Get location when permissions are granted
    LaunchedEffect(locationPermissionsState.allPermissionsGranted) {
        if (locationPermissionsState.allPermissionsGranted) {
            getCurrentLocation()
        }
    }

    // Use remember to track location changes
    val myPos by remember(userLocLatitude, userLocLongitude) {
        mutableStateOf(LatLng(userLocLatitude ?: 44.1484, userLocLongitude ?: 12.2359))
    }

    Log.d("MapsScreen", "Current myPos: lat=${myPos.latitude}, lng=${myPos.longitude}")

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myPos, 14f)
    }

    // Update camera when location changes
    LaunchedEffect(myPos) {
        if (userLocLatitude != null && userLocLongitude != null) {
            Log.d("MapsScreen", "Updating camera to new position: ${myPos.latitude}, ${myPos.longitude}")
            cameraPositionState.animate(
                CameraUpdateFactory.newLatLngZoom(myPos, 14f)
            )
        }
    }

    Scaffold(
        topBar = { TopBarSec("Fitness Locations", navController) },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            when {
                locationPermissionsState.allPermissionsGranted -> {
                    // All permissions granted, show map
                    GoogleMapView(
                        modifier = Modifier.matchParentSize(),
                        cameraPositionState = cameraPositionState,
                        context = context,
                        onMapLoaded = {
                            Log.d("MapsScreen", "Map loaded successfully")
                            isMapLoaded = true
                        },
                    )
                }
                else -> {
                    // Permissions not granted, show message and button to try again
                    LocationPermissionScreen(
                        onRequestPermission = {
                            locationPermissionsState.launchMultiplePermissionRequest()
                        }
                    )
                }
            }

            if (locationPermissionsState.allPermissionsGranted && (!isMapLoaded || isLocationLoading)) {
                AnimatedVisibility(
                    modifier = Modifier.matchParentSize(),
                    visible = !isMapLoaded || isLocationLoading,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colors.background),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally
                        ) {
                            CircularProgressIndicator()
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = if (isLocationLoading) "Getting your location..." else "Loading map..."
                            )
                        }
                    }
                }
            }
        }
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun LocationPermissionScreen(onRequestPermission: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Location Permission Required",
            style = MaterialTheme.typography.h6,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        Text(
            text = "This app needs access to your location to show your position on the map and provide location-based features.",
            style = MaterialTheme.typography.body2,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Button(
            onClick = onRequestPermission,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Grant Permission")
        }
    }
}

// Function to get current location using suspendCoroutine
@SuppressLint("MissingPermission")
suspend fun getCurrentLocationSimple(context: Context): Location? {
    val fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    return try {
        // First try to get last known location
        val lastLocation = suspendCoroutine<Location?> { continuation ->
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    Log.d("Location", "Got last location: $location")
                    continuation.resume(location)
                }
                .addOnFailureListener { exception ->
                    Log.e("Location", "Failed to get last location: ${exception.message}")
                    continuation.resume(null)
                }
        }

        if (lastLocation != null) {
            Log.d("Location", "Using last known location: ${lastLocation.latitude}, ${lastLocation.longitude}")
            return lastLocation
        }

        // If no last location, try to request current location
        Log.d("Location", "No last location, requesting current location...")

        // For now, return null and suggest manual location setting
        Log.w("Location", "No location available - user may need to open Google Maps first")
        null

    } catch (e: Exception) {
        Log.e("Location", "Error getting current location: ${e.message}")
        null
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    context: Context,
    onMapLoaded: () -> Unit,
) {
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var mapVisible by remember {
        mutableStateOf(true)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }

    // Use the updated position from the parent
    val currentPos = LatLng(userLocLatitude ?: 44.1484, userLocLongitude ?: 12.2359)
    Log.d("GoogleMapView", "Rendering with position: ${currentPos.latitude}, ${currentPos.longitude}")

    if (mapVisible) {
        Log.d("GoogleMapView", "Rendering GoogleMap component")
        GoogleMap(
            modifier = modifier,
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            uiSettings = uiSettings,
            onMapLoaded = {
                Log.d("GoogleMapView", "GoogleMap onMapLoaded callback triggered")
                onMapLoaded()
            },
            onPOIClick = {
                Log.d("GoogleMapView", "POI clicked: ${it.name}")
            },
        ) {
            Log.d("GoogleMapView", "Adding ${favPlaceList.size} markers")
            for (marker in favPlaceList) {
                Marker(
                    state = MarkerState(position = LatLng(marker.lat, marker.lon)),
                    title = marker.title,
                    snippet = "Tap to visit website",
                    onClick = { 
                        openGymWebsite(context, marker.websiteUrl, marker.title)
                        true // Consume the click event
                    }
                )
            }

            Marker(
                state = MarkerState(position = currentPos),
                title = user ?: "My Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )

            Log.d("GoogleMapView", "Added green marker at: ${currentPos.latitude}, ${currentPos.longitude}")
        }
    } else {
        Log.d("GoogleMapView", "Map not visible")
    }
}