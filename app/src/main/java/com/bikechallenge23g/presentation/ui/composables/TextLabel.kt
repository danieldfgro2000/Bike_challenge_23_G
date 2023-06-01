package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle

@Composable
fun TextLabel(
    modifier: Modifier = Modifier,
    inputText: String,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
    Text(
        modifier = modifier,
        text = inputText,
        style = textStyle,
        color = MaterialTheme.colorScheme.inverseSurface
    )
}