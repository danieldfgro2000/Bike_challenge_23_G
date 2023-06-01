package com.bikechallenge23g.presentation.navigation

import com.bikechallenge23g.R

sealed class NavigationRoutes(
    val route: String,
    val title: Int
) {
    object AddBike : NavigationRoutes(
        route = "add_bike",
        title = R.string.add_bike
    )
}
