package edu.unibo.tracker.maps

import android.util.Log
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
import androidx.navigation.NavController
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





@Composable
fun MapsScreen(navController: NavController) {
    Log.d("MapsScreen", "MapsScreen started")

    var isMapLoaded by remember {
        mutableStateOf(false)
    }

    val myPos = LatLng(userLocLatitude ?: 44.1484, userLocLongitude ?: 12.2359)
    Log.d("MapsScreen", "Position: lat=${myPos.latitude}, lng=${myPos.longitude}")

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(myPos, 14f)
    }
    Scaffold(
        topBar = { TopBarSec("Fitness Locations",navController)},

    ) {
        Box(modifier = Modifier.fillMaxSize()){
            GoogleMapView(modifier = Modifier.matchParentSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {
                Log.d("MapsScreen", "Map loaded successfully")
                isMapLoaded = true
            },)
            if (!isMapLoaded){
                AnimatedVisibility(
                    modifier = Modifier
                        .matchParentSize(),
                    visible = !isMapLoaded,
                    enter = EnterTransition.None,
                    exit = fadeOut()
                ) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .background(MaterialTheme.colors.background)
                            .wrapContentSize()
                    )}

            }
        }
        Spacer(modifier = Modifier.height(36.dp))
    }
}

@Composable
fun GoogleMapView(
    modifier: Modifier,
    cameraPositionState: CameraPositionState,
    onMapLoaded: ()->Unit,
){
    var mapProperties by remember {
        mutableStateOf(MapProperties(mapType = MapType.NORMAL))
    }
    var mapVisible by remember {
        mutableStateOf(true)
    }
    var uiSettings by remember { mutableStateOf(MapUiSettings(compassEnabled = false)) }
    val myPos = LatLng(userLocLatitude ?: 44.1484, userLocLongitude ?: 12.2359)

    if (mapVisible){
        Log.d("GoogleMapView", "Rendering GoogleMap component")
        GoogleMap(modifier = modifier,
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
        )
        {
            Log.d("GoogleMapView", "Adding ${favPlaceList.size} markers")
            for(marker in favPlaceList){
                Marker(
                    state = MarkerState(position = LatLng(marker.lat,marker.lon)),
                    title = marker.title
                )
            }

            Marker(
                state = MarkerState(position = myPos),
                title = user ?: "My Location",
                icon = BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            )
        }
    } else {
        Log.d("GoogleMapView", "Map not visible")
    }
}