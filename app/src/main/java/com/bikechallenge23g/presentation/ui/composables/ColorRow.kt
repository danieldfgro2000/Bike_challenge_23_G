package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.data.model.enums.BikeColor

@Composable
fun ColorRow(
    initialColor: BikeColor,
    onSelected: (color: BikeColor) -> Unit
) {
    val scrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        BikeColor.values().forEach { color ->
            Surface(
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        onSelected(color)
                    },
                shape = CircleShape,
                color = color.color,
                border =
                if (color.color == initialColor.color) {
                    BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)
                } else null
            ) {

            }
        }
    }
}