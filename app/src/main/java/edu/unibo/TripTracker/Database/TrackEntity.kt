package edu.unibo.tracker.Database


import androidx.room.Entity
import androidx.room.PrimaryKey
import edu.unibo.tracker.R

@Entity
data class Track(
    @PrimaryKey val id : String,
    var name: String?, // Route name
    var km: String?, // Distance in km
    var description: String?, // Route description
    var typeOfTrack: String?, // Indoor, Outdoor, Trail, etc
    val imageURL: String?
)

data class TrackStatic(
    @PrimaryKey val id : String,
    var name: String?,
    var km: String?,
    var description: String?,
    var typeOfTrack: String?, // Indoor, Outdoor, Trail, etc
    val imageRes: Int?
)

var trackStatic = listOf(
    TrackStatic(
        "1","City Park Loop","5","Easy jogging route through the city park with scenic views perfect for beginners",
        "Outdoor", R.drawable.city
    ),
    TrackStatic("4","Mountain Trail","12", "Challenging hiking trail with beautiful mountain views and varied terrain perfect for advanced fitness enthusiasts.",
        "Trail", R.drawable.mountain),

    TrackStatic("5","Riverside Path","8","Peaceful running route along the riverside, perfect for morning or evening fitness sessions",
        "Outdoor",R.drawable.riverside),

    TrackStatic("6","Indoor Circuit","0","High-intensity indoor workout circuit perfect for strength training and cardio sessions.",
        "Indoor",R.drawable.corto),

    TrackStatic("7","Coastal Route","15","Scenic coastal cycling route with ocean views and fresh sea breeze for outdoor cardio workouts",
        "Outdoor",R.drawable.coastal)

)