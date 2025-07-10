package edu.unibo.tracker.track

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import edu.unibo.tracker.Database.Track
import edu.unibo.tracker.Database.TrackEvent
import edu.unibo.tracker.Database.TrackViewModel
import edu.unibo.tracker.commonItem.DropdownField
import edu.unibo.tracker.commonItem.NumericField


@Composable
fun CardNewTrack(trackViewModel: TrackViewModel) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ExpCardNewTrack(trackViewModel)
    }
}

@Composable
private fun ExpCardNewTrack(trackViewModel: TrackViewModel) {

    var name by rememberSaveable { mutableStateOf("") }
    var km by rememberSaveable { mutableStateOf("") }
    var description by rememberSaveable { mutableStateOf("") }
    var trackType by rememberSaveable { mutableStateOf("") }
    // Auto-generate ID
    val id = remember { java.util.UUID.randomUUID().toString() }

    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
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
            text = "New Route",
            style = MaterialTheme.typography.h5,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (expanded) {
            Text(
                "Create a new fitness route"
                )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = name,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Route Name") },
                placeholder = { Text(text = "Route Name") },
                onValueChange = {
                    name = it
                },

                )
            NumericField(
                value = km,
                onValueChange = { km = it },
                label = "Distance (km)",
                placeholder = "Distance (km)",
                allowDecimals = true,
                maxValue = 200, // Max 200km
                modifier = Modifier.fillMaxWidth(0.8f)
            )
            OutlinedTextField(
                value = description,
                maxLines = 1,
                modifier = Modifier.fillMaxWidth(0.8f),
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Words,
                    keyboardType = KeyboardType.Text,
                    imeAction = ImeAction.Next
                ),
                label = { Text("Description") },
                placeholder = { Text(text = "Description") },
                onValueChange = {
                    description = it
                },
                ) //creare lista con image
            DropdownField(
                value = trackType,
                onValueChange = { trackType = it },
                label = "Track Type",
                options = listOf("Indoor", "Outdoor", "Trail"),
                modifier = Modifier.fillMaxWidth(0.8f)
            )

            var traccia = Track(id,name,km,description,trackType,"")
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedButton(
                onClick = {
                    addNewTrack(trackViewModel,traccia)
                    expanded = !expanded
                }
            ) {
                Text("Add Route",color = MaterialTheme.colors.onSurface)
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
        OutlinedButton(
            onClick = { expanded = !expanded }
        ) {
            Text(if (expanded) "-" else "+", fontSize = 21.sp, color = MaterialTheme.colors.onSurface)
        }
        Spacer(modifier = Modifier.height(12.dp))
    }
}

fun addNewTrack(trackViewModel: TrackViewModel, track: Track) {
    trackViewModel.onTriggerEvent(TrackEvent.AddTrack(track))
}

