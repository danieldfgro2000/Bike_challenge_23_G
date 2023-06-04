package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.bikechallenge23g.R
import com.bikechallenge23g.presentation.navigation.NavigationRoutes

@Composable
fun NoItemsPlaceholder(
    navController: NavController? = null,
    isRideFlow: Boolean = false
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (isRideFlow) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .dottedBorder(
                        color = MaterialTheme.colorScheme.tertiary,
                        gapSize = 25,
                        strokeWidth = 1.dp,
                        phase = 0f,
                        cornerRadius = 5.dp
                    )
            ) {
                Image(
                    modifier = Modifier.fillMaxWidth(),
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.BottomCenter,
                    painter = painterResource(id = R.drawable.no_ride_placeholder),
                    contentDescription = stringResource(id = R.string.missing_bike_card)
                )
            }
        } else {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 20.dp, end = 20.dp)
                    .dottedBorder(
                        color = MaterialTheme.colorScheme.tertiary,
                        gapSize = 25,
                        strokeWidth = 1.dp,
                        phase = 0f,
                        cornerRadius = 5.dp
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.missing_bike_card),
                    contentDescription = stringResource(id = R.string.missing_bike_card)
                )
            }
        }
        Box(modifier = Modifier.wrapContentHeight()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .zIndex(2f)
                    .padding(40.dp)
                    .align(Alignment.Center),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.background
                )
            ) {
                if (!isRideFlow) {
                    Text(
                        text = stringResource(id = R.string.no_bike),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Image(
                modifier = Modifier
                    .scale(if (isRideFlow) 1.3f else 1f)
                    .zIndex(1f)
                    .align(Alignment.Center)
                    .offset(x = -(50.dp)),
                painter = painterResource(id = R.drawable.dotted_line),
                contentScale = ContentScale.FillHeight,
                contentDescription = null
            )
        }

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            shape = RoundedCornerShape(4.dp),
            colors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onBackground,
                containerColor = Color(0xFF0066F5)
            ),
            onClick = { navController?.navigate(NavigationRoutes.AddEditBike.route) }
        ) {
            if (isRideFlow) {
                Text(text = stringResource(id = R.string.add_ride))
            } else {
                Text(text = stringResource(id = R.string.add_bike))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewNoBikePlaceholder() {
    NoItemsPlaceholder()
}

@Preview(showBackground = true)
@Composable
fun PreviewNoRidePlaceholder() {
    NoItemsPlaceholder(isRideFlow = true)
}
