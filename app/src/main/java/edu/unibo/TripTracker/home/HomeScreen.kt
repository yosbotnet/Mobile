package edu.unibo.tracker.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import edu.unibo.tracker.Database.WorkoutEvent
import edu.unibo.tracker.Database.WorkoutViewModel
import edu.unibo.tracker.Database.TrackEvent
import edu.unibo.tracker.Database.TrackViewModel
import edu.unibo.tracker.commonItem.FabMaps
import edu.unibo.tracker.home.bottomBar.BottomNavigationBar
import edu.unibo.tracker.home.topBar.TopBar
import edu.unibo.tracker.login.user
import edu.unibo.tracker.newsAnimation.SwipeCardAnimation
import edu.unibo.tracker.userLocLatitude
import edu.unibo.tracker.userLocLongitude

@Composable
fun HomeScreen(workoutViewModel: WorkoutViewModel, trackViewModel: TrackViewModel,navController:NavController) {

    Scaffold(
        bottomBar = { BottomNavigationBar(navController) },
        topBar = { TopBar(navController) },
        floatingActionButton = { FabMaps(navController ) },
        content = {
            LazyColumn(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()
            ) {
                item { Spacer(modifier = Modifier.height(18.dp)) }
                item {
                    Card(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth(1f),
                        shape = MaterialTheme.shapes.medium,
                        elevation = 4.dp
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                "Welcome back, $user!",
                                fontSize = 28.sp,
                                style = MaterialTheme.typography.h4.copy(
                                    fontWeight = FontWeight.Bold
                                ),
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.primary,
                                modifier = Modifier.padding(bottom = 8.dp)
                            )
                            Text(
                                "Ready to crush your fitness goals today?",
                                style = MaterialTheme.typography.subtitle1,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colors.onSurface.copy(alpha = 0.8f),
                                modifier = Modifier.padding(bottom = 12.dp)
                            )
                            Text(
                                "With FitTracker you can manage all your favorite workout routines and GPS routes, " +
                                        "track your fitness progress, and discover new exercises from the community.",
                                style = MaterialTheme.typography.body1,
                                textAlign = TextAlign.Justify,
                                modifier = Modifier.padding(4.dp)
                            )
                        }
                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }




                //--------------------Fitness Overview Section--------------------------------//
                item {
                    Text(
                        text = "Fitness Overview",
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }

                item {
                    LazyRow(modifier = Modifier.padding(start = 8.dp)) {
                        item {
                            Card(
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                                    .fillMaxWidth(0.8f),
                                shape = MaterialTheme.shapes.medium,
                                elevation = 6.dp,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        "🏋️ Upcoming Workout",
                                        style = MaterialTheme.typography.subtitle1.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colors.primary,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    var upcomingWorkout by remember { mutableStateOf<List<String?>>(emptyList()) }
                                    LaunchedEffect(key1 = workoutViewModel) {
                                        upcomingWorkout = getUpcomingWorkout(workoutViewModel)
                                    }
                                    var workoutName = upcomingWorkout.getOrNull(0)
                                    var workoutDate = upcomingWorkout.getOrNull(1)
                                    if (workoutName != null) {
                                        Text(
                                            "Workout: $workoutName",
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Text(
                                            "When: $workoutDate",
                                            style = MaterialTheme.typography.body2,
                                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                                        )
                                    } else {
                                        Text(
                                            "You haven't added any workouts yet.",
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }

                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        item {
                            Card(
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                                    .fillMaxWidth(0.8f),
                                shape = MaterialTheme.shapes.medium,
                                elevation = 6.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp),

                                ) {
                                    Text(
                                        "📊 Weekly Progress",
                                        style = MaterialTheme.typography.subtitle1.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colors.primary,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    var weeklyProgress by remember { mutableStateOf<List<String?>>(emptyList()) }
                                    LaunchedEffect(key1 = workoutViewModel) {
                                        weeklyProgress = getWeeklyProgress(workoutViewModel)
                                    }
                                    var goal = weeklyProgress.getOrNull(0)
                                    var progress = weeklyProgress.getOrNull(1)
                                    if (goal != null) {
                                        Text(
                                            "Goal: $goal",
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Text(
                                            "Progress: $progress",
                                            style = MaterialTheme.typography.body2,
                                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                                        )
                                    } else {
                                        Text(
                                            "No weekly progress yet.",
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        item {
                            Card(
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                                    .fillMaxWidth(0.8f),
                                shape = MaterialTheme.shapes.medium,
                                elevation = 6.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(16.dp)
                                ) {
                                    Text(
                                        "🏆 Best Workout",
                                        style = MaterialTheme.typography.subtitle1.copy(
                                            fontWeight = FontWeight.Bold
                                        ),
                                        color = MaterialTheme.colors.primary,
                                        modifier = Modifier.padding(bottom = 8.dp)
                                    )
                                    var bestWorkout by remember { mutableStateOf<List<String?>>(emptyList()) }
                                    LaunchedEffect(key1 = workoutViewModel) {
                                        bestWorkout = getBestWorkout(workoutViewModel)
                                    }
                                    var bestWorkoutName = bestWorkout.getOrNull(0)
                                    var bestCalories = bestWorkout.getOrNull(1)
                                    if (bestWorkoutName != null) {
                                        Text(
                                            "Workout: $bestWorkoutName",
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                        Text(
                                            "Calories: $bestCalories cal",
                                            style = MaterialTheme.typography.body2,
                                            color = MaterialTheme.colors.onSurface.copy(alpha = 0.7f)
                                        )
                                    } else {
                                        Text(
                                            "No best workout yet.",
                                            style = MaterialTheme.typography.body1.copy(
                                                fontWeight = FontWeight.Medium
                                            )
                                        )
                                    }
                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                    }
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                //--------------------fitness news section---------------------------------------//
                item { 
                    Text(
                        text = "Fitness News",
                        style = MaterialTheme.typography.h5.copy(
                            fontWeight = FontWeight.Bold
                        ),
                        color = MaterialTheme.colors.primary,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    ) 
                }
                item { Spacer(modifier = Modifier.height(12.dp)) }
                item { SwipeCardAnimation() }

                item { Spacer(modifier = Modifier.height(24.dp)) }
                //--------------------fine sezione notizie---------------------------------------//

                item { Spacer(modifier = Modifier.height(90.dp)) }
            }
        })
}

fun getUpcomingWorkout(workoutViewModel: WorkoutViewModel): List<String?> {
    workoutViewModel.onTriggerEvent(WorkoutEvent.GetUpcomingWorkout)
    var a = listOf(workoutViewModel.upcomingWorkout, workoutViewModel.upcomingDate)
    return a
}

fun getWeeklyProgress(workoutViewModel: WorkoutViewModel): List<String?> {
    workoutViewModel.onTriggerEvent(WorkoutEvent.GetWeeklyProgress)
    var b = listOf(workoutViewModel.weeklyGoal, workoutViewModel.weeklyProgress)
    return b
}

fun getBestWorkout(workoutViewModel: WorkoutViewModel): List<String?> {
    workoutViewModel.onTriggerEvent(WorkoutEvent.GetBestWorkout)
    var c = listOf(workoutViewModel.bestWorkoutName, workoutViewModel.bestWorkout)
    return  c
}
/*
fun getLatestTrack(trackViewModel: TrackViewModel) : String?{
    trackViewModel.onTriggerEvent(TrackEvent.GetLatestTrack)
    return trackViewModel.lastTrack
}*/




