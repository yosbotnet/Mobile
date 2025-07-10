package edu.unibo.tracker.Database

data class News(
    val title : String?,
    val content : String?
)

var newsList = listOf(
    News(
        "New Fitness Trends 2024", "High-intensity interval training (HIIT) continues to dominate fitness trends," +
                " with new variations focusing on functional movements and bodyweight exercises."
    ),
    News(
        "Outdoor Training Benefits",
        "Studies show that outdoor workouts provide additional mental health benefits compared to indoor training, " +
                "including improved mood and reduced stress levels."
    ),
    News(
        "Strength Training Revolution", "Modern strength training emphasizes compound movements and functional patterns " +
                "that translate to real-world activities and injury prevention."
    ),
    News(
        "Cardio Innovation",
        "New cardio equipment and training methods are making cardiovascular fitness more accessible and engaging," +
                " with technology integration and personalized training programs."
    ),
    News(
        "Recovery and Wellness", "The importance of recovery in fitness routines is gaining recognition, with new techniques " +
                "focusing on sleep optimization, nutrition timing, and active recovery methods."
    )
)

var newImageList = listOf(
    "https://blog.nasm.org/hubfs/fitness-trends.jpg",
    "https://www.freshairfitness.co.uk/assets/img/social/big-rig.jpg",
    "https://www.eatthis.com/wp-content/uploads/sites/4/2023/04/muscular-man-barbell-bicep-curls.jpg?quality=82&strip=1",
    "https://boyntonbeach.floridapremiercardio.com/wp-content/uploads/cardiac-stress-test-2107.jpg",
    "https://xcelerategyms.com/wp-content/uploads/2021/07/power-plate-image-1200x900-1-1024x768.jpg"
)