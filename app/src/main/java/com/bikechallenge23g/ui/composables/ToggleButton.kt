package com.bikechallenge23g.ui.composables


import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.FilledIconToggleButton
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconToggleButton
import androidx.compose.material3.Surface
import androidx.compose.material.Switch
import androidx.compose.material.SwitchColors
import androidx.compose.material3.SwitchDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color

@Composable
fun CustomSwitch(

) {
    val checked = remember { mutableStateOf(true) }
    Surface() {
        Switch(
            checked= checked.value,
            onCheckedChange= { checked.value = it },
            colors= androidx.compose.material.SwitchDefaults.colors(
                checkedThumbColor = Color(0xFFA6A6A6),
                checkedTrackColor = Color(0xFF0066F5),
                uncheckedTrackColor = Color(0xFFA6A6A6),
                uncheckedThumbColor = Color(0xFFA6A6A6)

            )
        )
    }
}