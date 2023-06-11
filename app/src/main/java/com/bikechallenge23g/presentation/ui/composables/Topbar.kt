package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Surface
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
import com.bikechallenge23g.data.model.enums.BikeColor

@Composable
fun TopBar(
    title: Int? = null,
    titleStr: String? = null,
    defaultBikeModel: String? = null,
    defaultBikeServiceIn: String? = null,
    icon: Int? = null,
    iconDescription: Int? = null,
    showIconDescription: Boolean = false,
    showNavBack: Boolean = false,
    showMoreOptions: Boolean = false,
    showDefaultBikeChangedBar: Boolean = false,
    onHideDefaultBikeBar: (Boolean) -> Unit = {},
    onDelete: () -> Unit = {},
    onEdit: () -> Unit = {},
    onNavBack: () -> Unit = {},
    onClose: () -> Unit = {}
) {

    Column {
        if (showDefaultBikeChangedBar) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(40.dp),
                color = BikeColor.ORANGE.color
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 20.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(
                        inputText = defaultBikeModel ?: "",
                        textStyle = MaterialTheme.typography.headlineMedium,
                        textColor = MaterialTheme.colorScheme.background
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = defaultBikeServiceIn ?: "",
                        textColor = MaterialTheme.colorScheme.background
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        modifier = Modifier.clickable {
                            onHideDefaultBikeBar(
                                showDefaultBikeChangedBar
                            )
                        },
                        painter = painterResource(id = R.drawable.icon_x),
                        tint = MaterialTheme.colorScheme.background,
                        contentDescription = stringResource(
                            id = iconDescription ?: R.string.empty
                        )
                    )

                }
            }
        }
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

}

@Preview
@Composable
private fun PreviewTopBarWithNavBack() {
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
private fun PreviewTopBarWithoutNavBack() {
    TopBar(
        title = R.string.bikes,
        icon = R.drawable.icon_x,
        showIconDescription = true,
        iconDescription = R.string.close,
        showNavBack = false
    ) {

    }
}

@Preview
@Composable
private fun PreviewTopBarWithoutDescription() {
    TopBar(
        title = R.string.bikes,
        icon = R.drawable.icon_x,
        showNavBack = false
    ) {

    }
}

@Preview
@Composable
private fun PreviewTopBarWithMore() {
    TopBar(
        title = R.string.bikes,
        icon = R.drawable.icon_x,
        showMoreOptions = true,
        showNavBack = false
    ) {

    }
}

@Preview
@Composable
private fun PreviewTopBarWithDefaultBike() {
    TopBar(
        title = R.string.bikes,
        icon = R.drawable.icon_x,
        showDefaultBikeChangedBar = false,
        showNavBack = false
    ) {

    }
}

@Preview
@Composable
private fun PreviewTopBarWithDefaultBikeTrue() {
    TopBar(
        title = R.string.bikes,
        icon = R.drawable.icon_x,
        showDefaultBikeChangedBar = true,
        defaultBikeModel = "Nukeproof Scout 290",
        defaultBikeServiceIn = "service in 100km",
        showNavBack = false
    ) {

    }
}
