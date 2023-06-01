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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.data.model.enums.BikeColors

@Composable
fun ColorRow(
    onSelected: (color: Color) -> Unit
) {
    val scrollState = rememberScrollState()
    var selectedColor by remember { mutableStateOf(Color.Red) }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
            .horizontalScroll(scrollState),
        horizontalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        BikeColors.values().forEach { color ->
            Surface(
                modifier = Modifier
                    .size(20.dp)
                    .clickable {
                        selectedColor = color.color
                        onSelected(color.color)
                    },
                shape = CircleShape,
                color = color.color,
                border =
                if (color.color == selectedColor) {
                    BorderStroke(2.dp, MaterialTheme.colorScheme.onBackground)
                } else null
            ) {

            }
        }
    }
}