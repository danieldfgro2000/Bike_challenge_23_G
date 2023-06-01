package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R

@Composable
fun TextLabel(
    modifier: Modifier = Modifier,
    inputText: String,
    isRequired: Boolean = false,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium
) {
    Row {
        Text(
            modifier = modifier.height(20.dp),
            text = inputText,
            style = textStyle,
            color = MaterialTheme.colorScheme.inverseSurface
        )
        if (isRequired) {
            Icon(
                painter = painterResource(id = R.drawable.icon_required),
                contentDescription = stringResource(
                    id = R.string.required_field
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewTextLabel() {
    TextLabel(inputText = stringResource(id = R.string.service_reminder), isRequired = true)
}