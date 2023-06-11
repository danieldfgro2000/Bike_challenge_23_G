package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.focus.focusTarget
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.theme.AppRed

@Composable
fun CustomCardWithDropDown(
    modifier: Modifier = Modifier,
    text: String,
    showIcon: Boolean = true,
    error: String?,
    expand: () -> Unit
) {
    val focusRequester = remember { FocusRequester() }
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .focusable()
            .focusRequester(focusRequester)
            .onFocusChanged { focusState -> isFocused = focusState.isFocused }
    ) {
        Card(
            modifier = Modifier
                .focusTarget()
                .height(50.dp)
                .fillMaxWidth()
                .clickable { expand() },
            shape = RoundedCornerShape(5.dp),
            border = BorderStroke(
                width = 1.dp,
                color = when {
                    error != null && !isFocused -> AppRed
                    else -> MaterialTheme.colorScheme.onBackground
                }
            )
        ) {
            Row(
                modifier = Modifier.padding(vertical = 10.dp, horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = text,
                    color = MaterialTheme.colorScheme.inverseSurface,
                    fontSize = MaterialTheme.typography.labelMedium.fontSize,
                )
                if (showIcon) {
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.padding(top = 10.dp),
                        painter = painterResource(id = R.drawable.icon_dropdown),
                        contentDescription = stringResource(id = R.string.chevron),
                        tint = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
        }
        if (error != null && !isFocused) {
            Text(
                modifier = Modifier.padding(top = 5.dp),
                text = error,
                style = MaterialTheme.typography.labelSmall.copy(color = AppRed)
            )
        }
    }
}

@Preview
@Composable
private fun PreviewCustomCardWithoutError() {
    CustomCardWithDropDown(text = "Text", error = null) {
    }
}

@Preview
@Composable
private fun PreviewCustomCardWithError() {
    CustomCardWithDropDown(text = "Text", error = "Error") {
    }
}
