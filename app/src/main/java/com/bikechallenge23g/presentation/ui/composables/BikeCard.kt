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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.Bike
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels

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
                        wheels = bike.wheelSize,
                        bikeTypes = bike.type
                    ).first
                ), contentDescription = stringResource(id = R.string.bike_wheels)
            )
            Image(
                modifier = modifier,
                painter = painterResource(
                    id = imageSelector(
                        wheels = bike.wheelSize,
                        bikeTypes = bike.type
                    ).second
                ),
                colorFilter = androidx.compose.ui.graphics.ColorFilter.tint(bike.bikeColor.color),
                contentDescription = stringResource(id = R.string.bike_middle)
            )
            Image(
                modifier = modifier,
                painter = painterResource(
                    id = imageSelector(
                        wheels = bike.wheelSize,
                        bikeTypes = bike.type
                    ).third
                ), contentDescription = stringResource(id = R.string.bike_over)
            )
        }
    }
}

@Composable
fun BikeCard(
    bikeTypes: BikeTypes,
    wheelSize: BikeWheels,
    bikeColor: BikeColors
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
                            bikeTypes = bikeTypes
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
                            bikeTypes = bikeTypes
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
                            bikeTypes = bikeTypes
                        ).third
                    ), contentDescription = stringResource(id = R.string.bike_over)
                )
            }
            TextLabel(
                height = 22.dp,
                inputText = bikeTypes.type,
                textStyle = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Composable
fun imageSelector(wheels: BikeWheels, bikeTypes: BikeTypes): Triple<Int, Int, Int> =
    bikeImageSelector(bikeTypes = bikeTypes, wheels = wheels)

@Composable
private fun wheelImageSelector(wheels: BikeWheels, smallWheelImage: Int, bigWheelImage: Int) =
    when (wheels) {
        BikeWheels.BIG -> bigWheelImage
        BikeWheels.SMALL -> smallWheelImage
    }

@Composable
private fun bikeImageSelector(bikeTypes: BikeTypes, wheels: BikeWheels) =
    when (bikeTypes) {
        BikeTypes.ELECTRIC -> {
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

        BikeTypes.HYBRID -> {
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

        BikeTypes.MTB -> {
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

        BikeTypes.ROAD_BIKE -> {
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
    bike: Bike,
    onMore: () -> Unit
) {
    Card(
        modifier = Modifier
            .padding(horizontal = 10.dp, vertical = 5.dp)
            .fillMaxWidth()
            .height(345.dp),
        shape = RoundedCornerShape(5.dp)
    ) {
        Box {
            Image(
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.FillBounds,
                painter = painterResource(id = R.drawable.bike_card_background),
                contentDescription = null
            )
            Image(
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(10.dp)
                    .clickable { onMore() },
                painter = painterResource(id = R.drawable.icon_more),
                contentDescription = null
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(345.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Spacer(modifier = Modifier.height(70.dp))
                BikeView(
                    bike,
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(1.8f)
                )
                Spacer(modifier = Modifier.height(30.dp))
                TextLabel(
                    modifier = Modifier.padding(start = 5.dp),
                    inputText = bike.model,
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.titleLarge,

                    )
                Row(modifier = Modifier.padding(start = 10.dp)) {
                    TextLabel(
                        inputText = stringResource(id = R.string.wheels),
                        textStyle = MaterialTheme.typography.labelLarge,

                        )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = bike.wheelSize.size,
                        textStyle = MaterialTheme.typography.labelLarge,

                        )

                }
                Row(modifier = Modifier.padding(start = 10.dp)) {
                    TextLabel(
                        inputText = stringResource(id = R.string.service_interval),
                        textStyle = MaterialTheme.typography.labelLarge,

                        )
                    Spacer(modifier = Modifier.width(5.dp))
                    TextLabel(
                        inputText = bike.serviceIn.toString(),
                        textStyle = MaterialTheme.typography.labelLarge
                    )
                    TextLabel(
                        inputText = bike.distanceUnit.name,
                        textStyle = MaterialTheme.typography.labelLarge
                    )

                }
                CustomSlider(
                    modifier = Modifier.padding(start = 10.dp, end = 10.dp, bottom = 10.dp),
                    progress = bike.serviceIn.toFloat(),
                    range = bike.serviceInterval.toFloat()
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewBikeCardWithDetails() {
    BikeCardWithDetails(bike = Bike(model = "NukeProof Scout 290")) {}
}

@Preview(showBackground = true)
@Composable
fun PreviewBikeCard() {
    BikeCard(
        bikeTypes = BikeTypes.ROAD_BIKE,
        wheelSize = BikeWheels.BIG,
        bikeColor = BikeColors.BLUE
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewBikeCardSmall() {
    BikeCard(
        bikeTypes = BikeTypes.MTB,
        wheelSize = BikeWheels.BIG,
        bikeColor = BikeColors.BLUE
    )
}