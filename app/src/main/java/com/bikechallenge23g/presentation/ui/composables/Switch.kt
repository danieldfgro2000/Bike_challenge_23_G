package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.material3.Surface
import androidx.compose.material.Switch
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