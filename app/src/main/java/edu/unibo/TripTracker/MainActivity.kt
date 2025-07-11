package edu.unibo.tracker

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.collectAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.rememberPermissionState
import edu.unibo.tracker.home.bottomBar.BottomNavigationBar
import edu.unibo.tracker.navigation.Navigation
import edu.unibo.tracker.ui.theme.FitTrackerTheme
import androidx.hilt.navigation.compose.hiltViewModel
import edu.unibo.tracker.preferences.ThemeViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint


var userLocLongitude: Double? = 12.2359
var userLocLatitude: Double? = 44.1484


private lateinit var fusedLocationClient: FusedLocationProviderClient

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location : Location? ->
                if (location != null) {
                    Log.d("MainActivity", "Got real location: lat=${location.latitude}, lng=${location.longitude}")
                    userLocLongitude = location.longitude
                    userLocLatitude = location.latitude
                } else {
                    Log.d("MainActivity", "No location available, using default coordinates")
                }
            }
            .addOnFailureListener { exception ->
                Log.e("MainActivity", "Failed to get location: ${exception.message}")
            }
        setContent {
            MainScreen()
        }
    }
}

@Composable
fun MainScreen(themeViewModel: ThemeViewModel = hiltViewModel()) {
    val navController = rememberNavController()
    
    FitTrackerTheme(
        darkTheme = themeViewModel.shouldUseDarkTheme()
    ) {
        Navigation(navController = navController)
    }
}
