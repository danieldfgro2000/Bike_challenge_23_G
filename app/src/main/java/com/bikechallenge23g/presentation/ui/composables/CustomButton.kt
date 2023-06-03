package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.theme.AppBlue
import com.bikechallenge23g.theme.AppLightGrey

@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean,
    callBack: () -> Unit
) {
    Button(
        modifier = modifier,
        enabled = enabled,
        shape = RoundedCornerShape(4.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = AppBlue,
            contentColor = Color.White,
            disabledContainerColor = AppBlue.copy(alpha = 0.4f),
            disabledContentColor = AppLightGrey
        ),
        onClick = { callBack() }
    ) {
        Text(
            text = text
        )
    }
}