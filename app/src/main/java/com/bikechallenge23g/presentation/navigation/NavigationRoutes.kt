package com.bikechallenge23g.presentation.navigation

import com.bikechallenge23g.R

sealed class NavigationRoutes(
    val route: String,
    val title: Int
) {
    object AddEditBike : NavigationRoutes(
        route = "add_edit_bike",
        title = R.string.add_bike
    )

    object AddEditRide : NavigationRoutes(
        route = "add_edit_ride",
        title = R.string.add_ride
    )
}
