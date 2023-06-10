package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R

@Composable
fun TopBar(
    title: Int? = null,
    titleStr: String? = null,
    icon: Int? = null,
    iconDescription: Int? = null,
    showIconDescription: Boolean = false,
    showNavBack: Boolean = false,
    showMoreOptions: Boolean = false,
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {},
    onNavBack: () -> Unit = {},
    onClose: () -> Unit = {}
) {

    Row(
        modifier = Modifier
            .height(50.dp)
            .padding(horizontal = 20.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (showNavBack) {
            Icon(
                modifier = Modifier.clickable { onNavBack() },
                imageVector = Icons.Default.ArrowBack,
                contentDescription = stringResource(id = R.string.nav_back),
                tint = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.width(10.dp))
        }
        title?.let {
            TitleTextLabel(inputText = stringResource(id = title))
        }
        titleStr?.let {
            TitleTextLabel(inputText = titleStr)
        }
        Spacer(modifier = Modifier.weight(1f))
        if (showMoreOptions) {
            MoreOptionsDropdown(
                onEditSelected = { onEdit() },
                onDeleteSelected = { onDelete() }
            )
        }
        icon?.let {
            Icon(
                modifier = Modifier.clickable { onClose() },
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

@Preview
@Composable
fun PreviewTopBarWithNavBack() {
    TopBar(
        title = R.string.bike,
        icon = R.drawable.icon_x,
        showIconDescription = true,
        iconDescription = R.string.close,
        showNavBack = true
    ) {

    }
}

@Preview
@Composable
fun PreviewTopBarWithoutNavBack() {
    TopBar(
        title = R.string.bike,
        icon = R.drawable.icon_x,
        showIconDescription = true,
        iconDescription = R.string.close,
        showNavBack = false
    ) {

    }
}

@Preview
@Composable
fun PreviewTopBarWithoutDescription() {
    TopBar(
        title = R.string.bike,
        icon = R.drawable.icon_x,
        showNavBack = false
    ) {

    }
}

@Preview
@Composable
fun PreviewTopBarWithMore() {
    TopBar(
        title = R.string.bike,
        icon = R.drawable.icon_x,
        showMoreOptions = true,
        showNavBack = false
    ) {

    }
}
