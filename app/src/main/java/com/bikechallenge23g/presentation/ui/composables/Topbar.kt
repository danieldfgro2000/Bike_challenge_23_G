package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R

@Composable
fun TopBar(
    title: Int,
    icon: Int? = null,
    iconDescription: Int? = null,
    showIconDescription: Boolean = false,
    topBarCallback: () -> Unit
) {
    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleTextLabel(inputText = stringResource(id = title))
        Spacer(modifier = Modifier.weight(1f))
        icon?.let {
            Icon(
                modifier = Modifier.clickable { topBarCallback() },
                painter = painterResource(id = icon),
                contentDescription = stringResource(
                    id = iconDescription ?: R.string.empty
                )
            )
        }
        iconDescription?.let {
            if (showIconDescription) {
                TextLabel(inputText = stringResource(id = iconDescription))
            }
        }
    }
}