package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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

