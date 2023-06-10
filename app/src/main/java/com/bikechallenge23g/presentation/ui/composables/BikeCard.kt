package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.theme.AppRed

@Composable
fun BikeView(
    bike: Bike,
    modifier: Modifier
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(modifier = Modifier.fillMaxWidth()) {
            Image(
                modifier = modifier,
                painter = painterResource(
                    id = imageSelector(
                        wheels = bike.wheelSize ?: BikeWheel.BIG,
                        bikeType = bike.type ?: BikeType.ROAD_BIKE
                    ).first
                ), contentDescription = stringResource(id = R.string.bike_wheels)
            )
            Image(
                modifier = modifier,
                painter = painterResource(
                    id = imageSelector(
                        wheels = bike.wheelSize ?: BikeWheel.BIG,
                        bikeType = bike.type ?: BikeType.ROAD_BIKE
                    ).second
                ),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(
                    bike.bikeColor?.color ?: Color.White
                ),
                contentDescription = stringResource(id = R.string.bike_middle)
            )
            Image(
                modifier = modifier,
                painter = painterResource(
                    id = imageSelector(
                        wheels = bike.wheelSize ?: BikeWheel.BIG,
                        bikeType = bike.type ?: BikeType.ROAD_BIKE
                    ).third
                ), contentDescription = stringResource(id = R.string.bike_over)
            )
        }
    }
}

@Composable
fun BikeCard(
    bikeType: BikeType,
    wheelSize: BikeWheel,
    bikeColor: BikeColor
) {
    Card(
        modifier = Modifier
            .width(300.dp)
            .height(200.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.background
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    modifier = Modifier
                        .width(300.dp)
                        .height(160.dp),
                    painter = painterResource(
                        id = imageSelector(
                            wheels = wheelSize,
                            bikeType = bikeType
                        ).first
                    ), contentDescription = stringResource(id = R.string.bike_wheels)
                )
                Image(
                    modifier = Modifier
                        .width(300.dp)
                        .height(160.dp),
                    painter = painterResource(
                        id = imageSelector(
                            wheels = wheelSize,
                            bikeType = bikeType
                        ).second
                    ),
                    colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(bikeColor.color),
                    contentDescription = stringResource(id = R.string.bike_middle)
                )
                Image(
                    modifier = Modifier
                        .width(300.dp)
                        .height(160.dp),
                    painter = painterResource(
                        id = imageSelector(
                            wheels = wheelSize,
                            bikeType = bikeType
                        ).third
                    ), contentDescription = stringResource(id = R.string.bike_over)
                )
            }
            TextLabel(
                height = 22.dp,
                inputText = bikeType.type,
                textStyle = MaterialTheme.typography.labelLarge
            )
        }
    }
}
fun imageSelector(wheels: BikeWheel, bikeType: BikeType): Triple<Int, Int, Int> =
    bikeImageSelector(bikeType = bikeType, wheels = wheels)

private fun wheelImageSelector(wheels: BikeWheel, smallWheelImage: Int, bigWheelImage: Int) =
    when (wheels) {
        BikeWheel.BIG -> bigWheelImage
        BikeWheel.SMALL -> smallWheelImage
    }

private fun bikeImageSelector(bikeType: BikeType, wheels: BikeWheel) =
    when (bikeType) {
        BikeType.ELECTRIC -> {
            Triple(
                wheelImageSelector(
                    wheels,
                    R.drawable.bike_electric_small_wheels,
                    R.drawable.bike_electric_big_wheels
                ),
                R.drawable.bike_electric_middle,
                R.drawable.bike_electric_over
            )
        }

        BikeType.HYBRID -> {
            Triple(
                wheelImageSelector(
                    wheels,
                    R.drawable.bike_hybrid_small_wheels,
                    R.drawable.bike_hybrid_big_wheels
                ),
                R.drawable.bike_hybrid_middle,
                R.drawable.bike_hybrid_over
            )
        }

        BikeType.MTB -> {
            Triple(
                wheelImageSelector(
                    wheels,
                    R.drawable.bike_mtb_small_wheels,
                    R.drawable.bike_mtb_big_wheels
                ),
                R.drawable.bike_mtb_middle,
                R.drawable.bike_mtb_over
            )
        }

        BikeType.ROAD_BIKE -> {
            Triple(
                wheelImageSelector(
                    wheels,
                    R.drawable.bike_roadbike_small_wheels,
                    R.drawable.bike_roadbike_big_wheels
                ),
                R.drawable.bike_roadbike_middle,
                R.drawable.bike_roadbike_over
            )
        }
    }

@Composable
fun BikeCardWithDetails(
    modifier: Modifier = Modifier,
    bike: Bike,
    isBikeDetailScreen: Boolean = false,
    ridesCount: String? = "",
    totalRidesDistance: String = "",
    onCardClicked: (Bike) -> Unit = {},
    onEditSelected: () -> Unit = {},
    onDeleteSelected: () -> Unit = {}
) {
    val remaining = ((bike.serviceIn ?: 0) - (bike.distance?.toInt() ?: 0)).toString()

    Card(
        modifier = modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .clickable { onCardClicked(bike) }
            .height(345.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Surface(color = MaterialTheme.colorScheme.background) {
            Box(contentAlignment = Alignment.Center) {
                if (!isBikeDetailScreen) {
                    Image(
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.FillBounds,
                        painter = painterResource(id = R.drawable.bike_card_background),
                        contentDescription = null
                    )
                    MoreOptionsDropdown(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(10.dp),
                        onEditSelected = { onEditSelected() },
                        onDeleteSelected = { onDeleteSelected() }
                    )
                }
                BikeView(
                    bike,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.8f)
                        .padding(bottom = 50.dp)
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(vertical = 10.dp),
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Bottom
                ) {
                    Spacer(modifier = Modifier.height(70.dp))

                    if (!isBikeDetailScreen) {
                        Spacer(modifier = Modifier.height(30.dp))
                        TextLabel(
                            modifier = Modifier.padding(start = 5.dp),
                            inputText = bike.model ?: "",
                            height = 30.dp,
                            textStyle = MaterialTheme.typography.titleLarge
                            )
                    }
                    Row(modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)) {
                        TextLabel(
                            inputText = stringResource(id = R.string.wheels),
                            textStyle = MaterialTheme.typography.labelMedium
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        TextLabel(
                            inputText = bike.wheelSize?.size ?: BikeWheel.BIG.size,
                            textStyle = MaterialTheme.typography.titleMedium
                        )
                    }
                    Row(modifier = Modifier.padding(start = 10.dp)) {
                        TextLabel(
                            inputText = stringResource(id = R.string.service_interval),
                            textStyle = MaterialTheme.typography.labelMedium
                            )
                        Spacer(modifier = Modifier.width(5.dp))
                        TextLabel(
                            inputText = remaining,
                            textStyle = MaterialTheme.typography.titleMedium,
                            textColor = if ((remaining.toIntOrNull() ?: 0) <= 0) AppRed else null
                        )
                        TextLabel(
                            inputText = bike.distanceUnit?.name ?: DistanceUnit.KM.name,
                            textStyle = MaterialTheme.typography.titleMedium,
                            textColor = if ((remaining.toIntOrNull() ?: 0) <= 0) AppRed else null
                        )
                    }
                    CustomSlider(
                        progress = bike.distance?.toFloat() ?: 0f,
                        range = bike.serviceIn?.toFloat() ?: 1f
                    )
                    if (isBikeDetailScreen) {
                        Row(modifier = Modifier.padding(horizontal = 10.dp)) {
                            TextLabel(
                                inputText = stringResource(id = R.string.rides),
                                textStyle = MaterialTheme.typography.labelMedium
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            TextLabel(
                                inputText = ridesCount!!,
                                textStyle = MaterialTheme.typography.titleMedium
                            )
                        }
                        Row(modifier = Modifier.padding(start = 10.dp, top = 5.dp)) {
                            TextLabel(
                                inputText = stringResource(id = R.string.total_ride_distance),
                                textStyle = MaterialTheme.typography.labelMedium
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            TextLabel(
                                inputText = totalRidesDistance,
                                textStyle = MaterialTheme.typography.titleMedium,
                                textColor = if ((remaining.toIntOrNull()
                                        ?: 0) <= 0
                                ) AppRed else null
                            )
                            TextLabel(
                                inputText = bike.distanceUnit?.name ?: DistanceUnit.KM.name,
                                textStyle = MaterialTheme.typography.titleMedium,
                                textColor = if ((remaining.toIntOrNull()
                                        ?: 0) <= 0
                                ) AppRed else null
                            )

                        }
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun PreviewBikeCardWithDetails() {
    BikeCardWithDetails(
        bike = Bike(
            model = "NukeProof Scout 290",
            serviceIn = 500,
            distance = 1000.0,
            distanceUnit = DistanceUnit.MILES
        ), onCardClicked = {}, onEditSelected = {}) {}
}

@Preview
@Composable
private fun PreviewBikeCardWithDetailsOnBikeDetailsScreen() {
    BikeCardWithDetails(
        isBikeDetailScreen = true,
        ridesCount = "3",
        totalRidesDistance = "450",
        bike = Bike(
            model = "NukeProof Scout 290",
            serviceIn = 500,
            distance = 1000.0,
            distanceUnit = DistanceUnit.MILES
        ), onCardClicked = {}, onEditSelected = {}) {}
}

@Preview(showBackground = true)
@Composable
private fun PreviewBikeCard() {
    BikeCard(
        bikeType = BikeType.ROAD_BIKE,
        wheelSize = BikeWheel.BIG,
        bikeColor = BikeColor.BLUE
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewBikeCardSmall() {
    BikeCard(
        bikeType = BikeType.MTB,
        wheelSize = BikeWheel.BIG,
        bikeColor = BikeColor.BLUE
    )
}