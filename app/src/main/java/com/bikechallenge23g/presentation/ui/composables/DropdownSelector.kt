package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R

@Composable
fun DropdownSelector(
    modifier: Modifier = Modifier,
    items: List<String>,
    selectedItem: String,
    onItemSelected: (String) -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Surface(modifier = modifier) {
        ClickableCardWithDropDown(selectedItem) { expanded.value = true }

        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    onClick = {
                        onItemSelected(item)
                        expanded.value = false
                    }
                ) {
                    Text(
                        text = item,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.inverseSurface
                    )
                }
            }
        }
    }
}

@Composable
fun IconWithDropdown(
    modifier: Modifier = Modifier,
    onEditSelected: () -> Unit,
    onDeleteSelected: () -> Unit
) {
    val expanded = remember { mutableStateOf(false) }
    Surface(modifier = modifier) {

        Image(
            modifier = Modifier.clickable { expanded.value = true },
            painter = painterResource(id = R.drawable.icon_more),
            contentDescription = null
        )
        DropdownMenu(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {

            DropdownMenuItem(
                onClick = {
                    onEditSelected()
                    expanded.value = false
                }
            ) {
                CustomMenuItem(icon = R.drawable.icon_edit, text = R.string.edit)
            }
            DropdownMenuItem(
                onClick = {
                    onDeleteSelected()
                    expanded.value = false
                }
            ) {
                CustomMenuItem(icon = R.drawable.icon_delete, text = R.string.delete)
            }

        }
    }

}

@Composable
fun CustomMenuItem(
    icon: Int,
    text: Int
) {
    Row(
        modifier = Modifier.wrapContentWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = stringResource(id = text)
        )
        Text(
            text = stringResource(id = text),
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.inverseSurface
        )
    }
}

