package edu.unibo.tracker.Database

data class FavouritePlace(
    val lat : Double,
    val lon: Double,
    val title : String?,
    val websiteUrl: String? = null
)

var favPlaceList = mutableListOf(
    FavouritePlace(
        44.14515404260142, 12.239008303644056, "FitActive",
        "https://www.fitactive.it"
    ),
    FavouritePlace(
        44.1568258743634, 12.243262267897261, "FitExpress",
        "https://www.fitexpress.it"
    ),
    FavouritePlace(
        44.22053205079787, 12.037161736374081, "MonkeyUp",
        "https://www.monkeyup.it"
    ),
    FavouritePlace(
        44.22522288373115, 12.036512940572845,"Almagym Forlì",
        "https://www.almagym.com"
    ),
    FavouritePlace(
        44.209733812356596, 12.039177114196171,"Forlì Wellness",
        "https://www.forliwellness.it"
    ),
    FavouritePlace(
        44.191579004235614, 12.115999509936097,"Beactive",
        "https://www.beactive.it"
    ),
    FavouritePlace(
        44.16940028348265, 12.279349505502248, "Technogym",
        "https://www.technogym.com"
    ),
)