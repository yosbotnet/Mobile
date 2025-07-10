package edu.unibo.tracker.profile

import android.Manifest
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import edu.unibo.tracker.login.user
import edu.unibo.tracker.commonItem.TopBarSec
import edu.unibo.tracker.R
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState
import coil.compose.AsyncImage
import coil.request.ImageRequest

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(navController: NavController) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        selectedImageUri = uri
    }

    Scaffold(
        topBar = { TopBarSec("Profile", navController) }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
        // Profile Picture Display
        Box(
            modifier = Modifier.size(120.dp),
            contentAlignment = Alignment.Center
        ) {
            if (selectedImageUri != null) {
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(selectedImageUri)
                        .crossfade(true)
                        .build(),
                    contentDescription = "Profile Picture",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    contentScale = ContentScale.Crop
                )
            } else {
                // Default profile icon
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    backgroundColor = MaterialTheme.colors.primary
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_profile),
                            contentDescription = "Default Profile",
                            modifier = Modifier.size(60.dp),
                            tint = MaterialTheme.colors.onPrimary
                        )
                    }
                }
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        Text("Welcome, $user!", style = MaterialTheme.typography.h5)
        Spacer(modifier = Modifier.height(16.dp))
        
        Text("Profile Picture", style = MaterialTheme.typography.h6)
        Spacer(modifier = Modifier.height(8.dp))
        
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Button(onClick = {
                imagePickerLauncher.launch("image/*")
            }) {
                Text("Gallery")
            }
            
            Button(onClick = {
                cameraPermissionState.launchPermissionRequest()
                if (cameraPermissionState.hasPermission) {
                    navController.navigate("camera")
                }
            }) {
                Text("Camera")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
            Button(onClick = {
                navController.navigate("login") {
                    popUpTo("home") { inclusive = true }
                }
            }) {
                Text("Logout")
            }
        }
    }
}