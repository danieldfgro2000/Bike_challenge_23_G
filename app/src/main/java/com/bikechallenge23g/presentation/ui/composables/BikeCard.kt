package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels

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