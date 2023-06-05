package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BikeScooter
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Ride
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.presentation.util.intTimeToHHmm
import com.bikechallenge23g.presentation.util.longEpochToDayOfWeek
import com.bikechallenge23g.presentation.util.longEpochToddMMyyyy
import com.bikechallenge23g.theme.AppDarkBlue
import java.util.Locale

@Composable
fun RideCard(
    ride: Ride,
    bikeModel: String,
    onCardClicked: (Ride) -> Unit,
    onEditSelected: () -> Unit,
    onDeleteSelected: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .clickable { onCardClicked(ride) },
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            contentColor = MaterialTheme.colorScheme.onBackground,
            containerColor = AppDarkBlue
        )
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            MoreOptionsDropdown(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp),
                onEditSelected = { onEditSelected() },
                onDeleteSelected = { onDeleteSelected() }
            )
            Column(modifier = Modifier.padding(10.dp)) {
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.BikeScooter,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = (longEpochToDayOfWeek(ride.date ?: 0).lowercase(Locale.ROOT)
                            .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString() } + " " + ride.name),
                        height = 30.dp,
                        textStyle = MaterialTheme.typography.titleLarge
                    )
                }
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        inputText = stringResource(id = R.string.bike_d)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = bikeModel,
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        inputText = stringResource(id = R.string.distance_d)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = ride.distance?.toInt().toString(),
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                    TextLabel(
                        inputText = ride.distanceUnit?.unit ?: DistanceUnit.KM.name,
                        textStyle = MaterialTheme.typography.titleMedium
                    )

                }
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        inputText = stringResource(id = R.string.duration_d)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = intTimeToHHmm(ride.duration ?: 0),
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                }
                Row(
                    modifier = Modifier.padding(5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TextLabel(
                        modifier = Modifier.align(Alignment.CenterVertically),
                        inputText = stringResource(id = R.string.date_d)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = longEpochToddMMyyyy(ride.date ?: 0),
                        textStyle = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }

    }

}

@Preview
@Composable
fun PreviewRideCard() {
    RideCard(ride = Ride(
        distanceUnit = DistanceUnit.KM,
        distance = 20.0,
        bikeId = 1,
        duration = 65,
        date = 14367,
        name = "Ride"
    ), bikeModel = "Nukeproof Scout 290", onCardClicked = {}, onEditSelected = {}) {

    }
}