package edu.unibo.tracker.profile

import android.Manifest
import android.annotation.SuppressLint
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
import androidx.hilt.navigation.compose.hiltViewModel
import edu.unibo.tracker.preferences.ThemeViewModel
import edu.unibo.tracker.preferences.ThemeMode
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.clickable
import androidx.compose.ui.text.font.FontWeight
import edu.unibo.tracker.profile.ProfileImageManager

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ProfileScreen(
    navController: NavController, 
    themeViewModel: ThemeViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val currentThemeMode by themeViewModel.themeMode.collectAsState()
    
    // Create ProfileImageManager instance
    val profileImageManager = remember { ProfileImageManager(context) }
    
    // Load saved profile image on startup
    LaunchedEffect(Unit) {
        selectedImageUri = profileImageManager.getProfileImageUri()
    }
    
    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)
    
    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        uri?.let {
            selectedImageUri = it
            // Save the image to internal storage
            profileImageManager.saveProfileImage(it)
        }
    }

    Scaffold(
        topBar = { TopBarSec("Profile", navController) }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile Picture Display
            item {
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
            }
            
            item {
                Text("Welcome, $user!", style = MaterialTheme.typography.h5)
            }
            
            // Profile Picture Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text("Profile Picture", style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold))
                        Spacer(modifier = Modifier.height(12.dp))
                        
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
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
                            
                            // Show remove button only if there's an image
                            if (selectedImageUri != null) {
                                OutlinedButton(
                                    onClick = {
                                        profileImageManager.deleteProfileImage()
                                        selectedImageUri = null
                                    }
                                ) {
                                    Text("Remove Photo", color = MaterialTheme.colors.error)
                                }
                            }
                        }
                    }
                }
            }

            // Theme Selection Section
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = 4.dp
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp)
                    ) {
                        Text(
                            "🎨 App Theme",
                            style = MaterialTheme.typography.h6.copy(fontWeight = FontWeight.Bold),
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                        
                        ThemeMode.values().forEach { mode ->
                            val isSelected = currentThemeMode == mode
                            val (icon, label) = when (mode) {
                                ThemeMode.SYSTEM -> "📱" to "System Default"
                                ThemeMode.LIGHT -> "☀️" to "Light Theme"
                                ThemeMode.DARK -> "🌙" to "Dark Theme"
                            }
                            
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { themeViewModel.setThemeMode(mode) }
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                RadioButton(
                                    selected = isSelected,
                                    onClick = { themeViewModel.setThemeMode(mode) },
                                    colors = RadioButtonDefaults.colors(
                                        selectedColor = MaterialTheme.colors.primary
                                    )
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Text(
                                    text = "$icon $label",
                                    style = MaterialTheme.typography.body1.copy(
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                                    ),
                                    color = if (isSelected) MaterialTheme.colors.primary else MaterialTheme.colors.onSurface
                                )
                            }
                        }
                    }
                }
            }
            
            // Logout Section
            item {
                Button(
                    onClick = {
                        navController.navigate("login") {
                            popUpTo("home") { inclusive = true }
                        }
                    },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = MaterialTheme.colors.error
                    )
                ) {
                    Text("Logout", color = MaterialTheme.colors.onError)
                }
            }
        }
    }
}