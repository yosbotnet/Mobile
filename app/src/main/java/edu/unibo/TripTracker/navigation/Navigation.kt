package edu.unibo.tracker.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import edu.unibo.tracker.Database.WorkoutViewModel
import edu.unibo.tracker.Database.TrackViewModel
import edu.unibo.tracker.camera.CameraScreen
import edu.unibo.tracker.home.HomeScreen
import edu.unibo.tracker.login.LoginScreen
import edu.unibo.tracker.login.RegistrationScreen
import edu.unibo.tracker.maps.MapsScreen
import edu.unibo.tracker.profile.EditProfileScreen
import edu.unibo.tracker.profile.ProfileScreen

import edu.unibo.tracker.track.TrackScreen
import edu.unibo.tracker.workout.WorkoutScreen


@Composable
fun Navigation(navController: NavHostController) {
    // Create ViewModels at this level to share across destinations
    val workoutViewModel = hiltViewModel<WorkoutViewModel>()
    val trackViewModel = hiltViewModel<TrackViewModel>()
    
    NavHost(navController, startDestination = NavigationItem.Login.route
    ) {
        composable(NavigationItem.Home.route) {
            HomeScreen(workoutViewModel, trackViewModel, navController)
        }
        composable(NavigationItem.MyWorkouts.route) {
            WorkoutScreen(navController, workoutViewModel)
        }
        composable(NavigationItem.MyTracks.route) {
            TrackScreen(navController, trackViewModel)
        }
        composable(NavigationItem.Maps.route){
            MapsScreen(navController)
        }
        composable(NavigationItem.Camera.route){
            CameraScreen(navController)
        }
        composable(NavigationItem.Login.route){
            LoginScreen(navController)
        }
        composable(NavigationItem.Registration.route){
            RegistrationScreen(navController)
        }
        composable(NavigationItem.Profile.route){
            ProfileScreen(navController)
        }
        composable("edit_profile"){
            EditProfileScreen(navController)
        }
    }
}