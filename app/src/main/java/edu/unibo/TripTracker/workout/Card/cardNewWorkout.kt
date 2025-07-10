package edu.unibo.tracker.workout.Card

import android.Manifest
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import edu.unibo.tracker.Database.Workout
import edu.unibo.tracker.Database.WorkoutEvent
import edu.unibo.tracker.Database.WorkoutViewModel
import edu.unibo.tracker.Database.TrackViewModel
import edu.unibo.tracker.R
import edu.unibo.tracker.camera.CameraPreview
import edu.unibo.tracker.camera.imageUri
import edu.unibo.tracker.commonItem.DropdownField
import edu.unibo.tracker.commonItem.NumericField
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberPermissionState

import java.util.*


@Composable
fun CardAddWorkout(workoutViewModel: WorkoutViewModel, navController: NavController) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp),
        elevation = 4.dp
    ) {
        AddWorkout(workoutViewModel, navController)
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun AddWorkout(workoutViewModel: WorkoutViewModel, navController: NavController) {
    var id by rememberSaveable { mutableStateOf("") }
    var activityType by rememberSaveable { mutableStateOf("") }
    var workoutName by rememberSaveable { mutableStateOf("") }
    var intensity by rememberSaveable { mutableStateOf("") }
    var category by rememberSaveable { mutableStateOf("") }
    var difficulty by rememberSaveable { mutableStateOf("") }
    var equipment by rememberSaveable { mutableStateOf("") }
    var imgUrl by rememberSaveable { mutableStateOf("") }

    val cameraPermissionState = rememberPermissionState(permission = Manifest.permission.CAMERA)

    // Duration and calories as proper numeric inputs  
    var duration by rememberSaveable { mutableStateOf("") }
    var calories by rememberSaveable { mutableStateOf("") }


    //------------------------------------------------//
    var expanded by rememberSaveable { (mutableStateOf(false)) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    var actWorkout = Workout(
        id, activityType, workoutName,
        intensity,
        category,
        difficulty,
        equipment, duration, calories,
        imageUri.toString(), false
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = extraPadding.coerceAtLeast(0.dp))
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioLowBouncy,
                    stiffness = Spring.StiffnessVeryLow
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            "New Workout",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (expanded) {
            Spacer(modifier = Modifier.height(12.dp))
            Button(onClick = {
                cameraPermissionState.launchPermissionRequest()
                if (cameraPermissionState.hasPermission) {
                    navController.navigate("camera")
                }
            }) {


                Row(
                    modifier = Modifier.fillMaxWidth(0.4f),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Icon(
                        painterResource(id = R.drawable.ic_baseline_photo_camera_24),
                        contentDescription = ""
                    )

                    Text("Add a photo")
                }
            }
            OutlinedTextField(
                value = id,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Id") },
                placeholder = { Text(text = "Id") },
                onValueChange = {
                    id = it
                },
            )
            OutlinedTextField(
                value = activityType,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Activity Type") },
                placeholder = { Text("Activity Type") },
                onValueChange = {
                    activityType = it
                },

                )
            OutlinedTextField(
                value = workoutName,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Workout Name") },
                placeholder = { Text("Workout Name") },
                onValueChange = {
                    workoutName = it
                },

                )
            DropdownField(
                value = intensity,
                onValueChange = { intensity = it },
                label = "Intensity",
                options = listOf("Low", "Medium", "High", "Very High"),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            DropdownField(
                value = category,
                onValueChange = { category = it },
                label = "Category",
                options = listOf("Cardio", "Strength", "Flexibility", "Sports", "Mixed"),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            DropdownField(
                value = difficulty,
                onValueChange = { difficulty = it },
                label = "Difficulty",
                options = listOf("Beginner", "Intermediate", "Advanced", "Expert"),
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            OutlinedTextField(
                value = equipment,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Equipment") },
                placeholder = { Text("Equipment") },
                onValueChange = {
                    equipment = it
                },
            )
            Spacer(modifier = Modifier.height(12.dp))

            /*
            OutlinedTextField(
                value = imgUrl,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Link") },
                placeholder = { Text("Link") },
                onValueChange = {
                    imgUrl = it
                },
            )*/

            NumericField(
                value = duration,
                onValueChange = { duration = it },
                label = "Duration (minutes)",
                placeholder = "Duration (minutes)",
                allowDecimals = false,
                maxValue = 600, // Max 10 hours
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            NumericField(
                value = calories,
                onValueChange = { calories = it },
                label = "Calories",
                placeholder = "Calories",
                allowDecimals = false,
                maxValue = 5000, // Max reasonable calories
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Button(
                onClick = {
                    addNewWorkout(workoutViewModel, actWorkout)
                    expanded= !expanded
                },
            ) {
                Text("Add")
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        OutlinedButton(
            onClick = { expanded = !expanded }
        ) {
            Text(
                if (expanded) "-" else "+",
                fontSize = 21.sp,
                color = MaterialTheme.colors.onSurface
            )
        }
        Spacer(modifier = Modifier.height(12.dp))
    }

}

fun addNewWorkout(workoutViewModel: WorkoutViewModel, workout: Workout) {
    workoutViewModel.onTriggerEvent(WorkoutEvent.AddWorkout(workout))
}



