package edu.unibo.tracker.navigation

import edu.unibo.tracker.R

sealed class NavigationItem(var route: String, var icon: Int, var title: String) {
    object Home : NavigationItem("home", R.drawable.ic_home, "Home")
    object MyWorkouts : NavigationItem("workouts", R.drawable.ic_fitness_workouts, "Workouts")
    object MyTracks : NavigationItem("track", R.drawable.ic_track, "Routes")
    object Maps : NavigationItem("maps", R.drawable.ic_pin, "Maps")
    object Camera : NavigationItem("camera", R.drawable.ic_home, "Camera")
    object Login : NavigationItem("login", R.drawable.ic_home, "Login")
    object Registration : NavigationItem("registration", R.drawable.ic_home, "Registration")
    object Profile : NavigationItem("profile", R.drawable.ic_home, "Profile")
}
