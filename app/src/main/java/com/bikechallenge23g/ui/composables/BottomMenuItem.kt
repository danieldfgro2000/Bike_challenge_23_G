package com.bikechallenge23g.ui.composables

import com.bikechallenge23g.R

sealed class BottomMenuItem(
    val route: String,
    val icon_active: Int,
    val title: Int
) {

    object Bikes : BottomMenuItem(
        route = "bikes",
        icon_active = R.drawable.icon_bikes_active,
        title = R.string.bikes
    )

    object Rides : BottomMenuItem(
        route = "rides",
        icon_active = R.drawable.rides_active,
        title = R.string.rides
    )

    object Settings : BottomMenuItem(
        route = "settings",
        icon_active = R.drawable.settings_active,
        title = R.string.settings
    )
}
