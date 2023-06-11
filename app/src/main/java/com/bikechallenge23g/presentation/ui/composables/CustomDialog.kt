package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.theme.AppDarkBlue

@Composable
fun CustomDialog(
    title: String,
    onCancel: () -> Unit,
    onConfirm: () -> Unit
) {
    AlertDialog(
        modifier = Modifier.height(200.dp),
        onDismissRequest = { onCancel() },
        title = {},
        text = {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextLabel(
                    inputText = title,
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.titleLarge
                )
                TextLabel(
                    inputText = stringResource(id = R.string.bike_will_be_deleted),
                    height = 24.dp,
                    textStyle = MaterialTheme.typography.labelLarge
                )
            }
        },
        buttons = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp),
                    onClick = { onCancel() }) {
                    TextLabel(
                        inputText = stringResource(id = R.string.cancel),
                        textStyle = MaterialTheme.typography.labelMedium,
                        textColor = MaterialTheme.colorScheme.secondary
                    )
                }

                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp),
                    onClick = { onConfirm() }) {
                    TextLabel(
                        inputText = stringResource(id = R.string.delete),
                        textStyle = MaterialTheme.typography.labelMedium
                    )
                }
            }
        },
        backgroundColor = AppDarkBlue
    )
}

@Preview
@Composable
private fun PreviewCustomDialog() {
    CustomDialog(title = "Cannondale", onCancel = {}, onConfirm = { })
}