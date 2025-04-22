package cat.deim.asm34.patinfly.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash")
    object Login : Screen("login")
    object Main : Screen("main")
    object Profile : Screen("profile")
    object BikeList : Screen("bikes")
    object Pricing : Screen("pricing")
    object BikeDetail : Screen("bike_detail/{bikeId}") {
        fun createRoute(bikeId: String) = "bike_detail/$bikeId"
    }


}