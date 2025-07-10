package edu.unibo.tracker.Database

data class FavouritePlace(
    val lat : Double,
    val lon: Double,
    val title : String?
)

var favPlaceList = mutableListOf(
    FavouritePlace(
        44.14515404260142, 12.239008303644056, "FitActive"
    ),
    FavouritePlace(
        44.1568258743634, 12.243262267897261, "FitExpress"
    ),
    FavouritePlace(
        44.22053205079787, 12.037161736374081, "MonkeyUp"
    ),
    FavouritePlace(
        44.22522288373115, 12.036512940572845,"Almagym Forli"
    ),
    FavouritePlace(
        44.209733812356596, 12.039177114196171,"Forli Wellness"
    ),
    FavouritePlace(
        44.191579004235614, 12.115999509936097,"Beactive"
    ),
    FavouritePlace(
        44.16940028348265, 12.279349505502248, "Technogym"
    ),

)