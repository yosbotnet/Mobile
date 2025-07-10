package edu.unibo.tracker.track

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import edu.unibo.tracker.Database.Track
import edu.unibo.tracker.Database.TrackStatic

@Composable
fun CardPosTracks(track: Track) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ExpCardTrack(track)
    }
}

@Composable
private fun ExpCardTrack(track: Track) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
        ) {
            track.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold
                )
            }
            if (expanded) {
                Column() {
                    track.km?.let { Text("$it Km") }
                    track.description?.let { Text(it) }
                    track.typeOfTrack?.let { Text("Tipo di traccia: $it") }
                }
            }
        }
        OutlinedButton(
            onClick = { expanded = !expanded }
        ) {
            Text(if (expanded) "Show less" else "Show more", color = MaterialTheme.colors.onSurface)
        }
    }
}

@Composable
fun CardPosTracksStatic(track: TrackStatic) {
    Card(
        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        ExpCardTrackStatic(track)
    }
}

@Composable
private fun ExpCardTrackStatic(track: TrackStatic) {

    var expanded by rememberSaveable { mutableStateOf(false) }
    val extraPadding by animateDpAsState(
        if (expanded) 48.dp else 0.dp
    )
    Row(
        modifier = Modifier
            .padding(24.dp)
            .animateContentSize(
                animationSpec = spring(
                    dampingRatio = Spring.DampingRatioMediumBouncy,
                    stiffness = Spring.StiffnessLow
                )
            )
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(bottom = extraPadding.coerceAtLeast(0.dp))
        ) {
            track.name?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.subtitle1,
                    fontWeight = FontWeight.SemiBold
                )
            }
            if (expanded) {


                Column() {
                    Spacer(modifier = Modifier.height(4.dp))
                    track.imageRes?.let { painterResource(id = it) }
                        ?.let {
                            Image(
                                painter = it, contentDescription = "",
                                modifier = Modifier.clip(
                                    RoundedCornerShape(5.dp)
                                ),

                                )
                        }
                    Spacer(modifier = Modifier.height(4.dp))
                    track.km?.let { Text("$it Km") }
                    track.description?.let { Text(it) }
                    track.typeOfTrack?.let { Text("Tipo di traccia: $it") }
                }
            }
        }
        OutlinedButton(
            onClick = { expanded = !expanded }
        ) {
            Text(if (expanded) "Show less" else "Show more", color = MaterialTheme.colors.onSurface)
        }
    }
}



