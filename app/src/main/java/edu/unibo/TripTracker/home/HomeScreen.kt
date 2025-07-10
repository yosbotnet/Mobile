package edu.unibo.tracker.home

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material.*
import androidx.compose.runtime.Composable
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
                    Surface(
                        modifier = Modifier
                            .padding(6.dp)
                            .fillMaxWidth(1f), shape = MaterialTheme.shapes.medium
                    ) {
                        Column(
                            modifier = Modifier
                                .padding(4.dp)
                                .fillMaxWidth(0.9f),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text(
                                "Welcome $user",
                                fontSize = 28.sp,
                                style = MaterialTheme.typography.h4,
                                textAlign = TextAlign.Left,
                                modifier = Modifier.padding(4.dp)
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
                        style = MaterialTheme.typography.h5
                    )
                }

                item {
                    LazyRow(modifier = Modifier.padding(start = 8.dp)) {
                        item {
                            Surface(
                                color = MaterialTheme.colors.background,
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                                    .fillMaxWidth(0.8f)
                                    .border(
                                        2.dp,
                                        MaterialTheme.colors.surface,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                shape = MaterialTheme.shapes.medium,
                                elevation = 4.dp,
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        "Upcoming Workout",
                                        style = MaterialTheme.typography.subtitle1,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    var upcomingWorkout = getUpcomingWorkout(workoutViewModel)
                                    var workoutName = upcomingWorkout[0]
                                    var workoutDate = upcomingWorkout[1]
                                    Text(
                                        "Workout: $workoutName",
                                        style = MaterialTheme.typography.body1
                                    )
                                    Text(
                                        "When: $workoutDate",
                                        style = MaterialTheme.typography.body1
                                    )

                                }


                            }

                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }
                        item {
                            Surface(
                                color = MaterialTheme.colors.background,
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                                    .fillMaxWidth(0.8f)
                                    .border(
                                        2.dp,
                                        MaterialTheme.colors.surface,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                shape = MaterialTheme.shapes.medium,
                                elevation = 4.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp),

                                ) {
                                    Text(
                                        "Weekly Progress",
                                        style = MaterialTheme.typography.subtitle1,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    var weeklyProgress = getWeeklyProgress(workoutViewModel)
                                    var goal = weeklyProgress[0]
                                    var progress = weeklyProgress[1]
                                    Text(
                                        "Goal: $goal",
                                        style = MaterialTheme.typography.body1
                                    )
                                    Text(
                                        "Progress: $progress",
                                        style = MaterialTheme.typography.body1
                                    )

                                }
                            }
                        }
                        item {
                            Spacer(modifier = Modifier.width(8.dp))
                        }

                        item {
                            Surface(
                                color = MaterialTheme.colors.background,
                                modifier = Modifier
                                    .padding(top = 12.dp, bottom = 12.dp)
                                    .fillMaxWidth(0.8f)
                                    .border(
                                        2.dp,
                                        MaterialTheme.colors.surface,
                                        shape = MaterialTheme.shapes.medium
                                    ),
                                shape = MaterialTheme.shapes.medium,
                                elevation = 4.dp
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(12.dp)
                                ) {
                                    Text(
                                        "Best Workout",
                                        style = MaterialTheme.typography.subtitle1,
                                        fontWeight = FontWeight.SemiBold
                                    )
                                    var bestWorkout = getBestWorkout(workoutViewModel)
                                    var bestWorkoutName = bestWorkout[0]
                                    var bestCalories = bestWorkout[1]
                                    Text(
                                        "Workout: $bestWorkoutName",
                                        style = MaterialTheme.typography.body1
                                    )
                                    Text("Calories: $bestCalories cal")
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
                item { Text(text = "Fitness News", style = MaterialTheme.typography.h5) }
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




