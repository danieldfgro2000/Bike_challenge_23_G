package com.bikechallenge23g.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp

@Composable
fun TextLabel(
    inputText: String,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
    Text(
        modifier = Modifier.padding(10.dp),
        text = inputText,
        style = textStyle,
        color = MaterialTheme.colorScheme.inverseSurface
    )
}