package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
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
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.theme.AppDarkBlue

@Composable
fun CustomDialog(
    bike: Bike,
    hideDialog: () -> Unit,
    deleteBike: (Bike) -> Unit
) {
    AlertDialog(
        onDismissRequest = { hideDialog() },
        title = {},
        text = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextLabel(
                    inputText = bike.model,
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.titleMedium
                )
                TextLabel(
                    inputText = stringResource(id = R.string.bike_will_be_deleted),
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
                        contentColor = MaterialTheme.colorScheme.secondary,
                        containerColor = Color.Transparent
                    ),
                    shape = RoundedCornerShape(5.dp),
                    onClick = { hideDialog() }) {
                    TextLabel(inputText = stringResource(id = R.string.cancel))
                }

                Button(
                    modifier = Modifier.weight(1f),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                    shape = RoundedCornerShape(5.dp),
                    onClick = { deleteBike(bike) }) {
                    TextLabel(inputText = stringResource(id = R.string.delete))
                }
            }
        },
        backgroundColor = AppDarkBlue
    )
}

@Preview
@Composable
private fun PreviewCustomDialog() {
    CustomDialog(bike = Bike(model = "Cannondale"), hideDialog = {}, deleteBike = { })
}