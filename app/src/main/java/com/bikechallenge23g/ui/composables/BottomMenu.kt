package com.bikechallenge23g.ui.composables

import android.provider.Settings.Global.getString
import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.bikechallenge23g.R

@Composable
fun BottomMenu(navController: NavController) {
    val menuItems =
        listOf(
            BottomMenuItem.Bikes,
            BottomMenuItem.Rides,
            BottomMenuItem.Settings
        )

    BottomNavigation(
        backgroundColor = colorResource(id = R.color.app_medium_grey),
        contentColor = colorResource(id = R.color.app_blue)
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route

        menuItems.forEach {

            val isSelected = currentRoute == it.route

            BottomNavigationItem(
                label = {
                    Text(
                        text = stringResource(id = it.title),
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                alwaysShowLabel = true,
                selected = isSelected,
                unselectedContentColor = colorResource(id = R.color.white),
                icon = {
                    Icon(
                        painter = painterResource(id = it.icon_active),
                        contentDescription = stringResource(id = it.title)
                    )
                },
                onClick = {
                    navController.navigate(it.route) {
                        navController.graph.startDestinationRoute?.let { route ->
                            popUpTo(route) { saveState = true }
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}