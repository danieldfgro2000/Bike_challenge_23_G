package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R

@Composable
fun CustomCardWithDropDown(
    text: String,
    showIcon: Boolean = true,
    expand: () -> Unit
) {
    Card(
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth()
            .clickable { expand() },
        shape = RoundedCornerShape(5.dp),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.onBackground)
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
}