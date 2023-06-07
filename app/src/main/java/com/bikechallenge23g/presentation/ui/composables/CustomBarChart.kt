package com.bikechallenge23g.presentation.ui.composables

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.DistanceUnit
import com.bikechallenge23g.theme.AppLightGreen
import com.bikechallenge23g.theme.AppOrange
import java.util.Locale


@Composable
fun CustomBarChart(
    values: List<Triple<BikeType, Float, Color>>,
    totalKm: Double
) {
    val axisColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.4f)
    val density = LocalDensity.current
    val axisStrokeWidth = with(density) { 3.dp.toPx() }
    val lineStrokeWidth = with(density) { 1.dp.toPx() }
    var chartRowHeight by remember { mutableStateOf(0) }


    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp),
        shape = RoundedCornerShape(5.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onBackground
        )

    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Start
            ) {
                Image(
                    painter = painterResource(id = R.drawable.icon_stats),
                    contentDescription = stringResource(id = R.string.all_rides_statistics),
                    colorFilter = ColorFilter.tint(MaterialTheme.colorScheme.onBackground)
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextLabel(
                    inputText = stringResource(id = R.string.all_rides_statistics),
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.titleLarge
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(330.dp)
                    .padding(horizontal = 10.dp)
                    .onGloballyPositioned { layoutCoordinates ->
                        chartRowHeight = layoutCoordinates.size.height
                        Log.e("Chart height = ", "$chartRowHeight")
                    }
                    .drawBehind {
                        drawLine(
                            color = axisColor,
                            start = Offset(0f, 0f),
                            end = Offset(0f, size.height),
                            strokeWidth = lineStrokeWidth
                        )
                        drawLine(
                            color = axisColor,
                            start = Offset(size.width, 0f),
                            end = Offset(size.width, size.height),
                            strokeWidth = lineStrokeWidth
                        )

                        repeat((size.height / 100).toInt() + 1) {
                            drawLine(
                                color = axisColor,
                                start = Offset(0f, size.height - 100 * it),
                                end = Offset(size.width, size.height - 100 * it),
                                strokeWidth = lineStrokeWidth
                            )
                        }
                        drawLine(
                            color = axisColor,
                            start = Offset(0f, size.height - 100),
                            end = Offset(size.width, size.height - 100),
                            strokeWidth = axisStrokeWidth
                        )

                    },
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                values.forEach { value ->
                    Bar(
                        bikeType = value.first,
                        value = value.second,
                        color = value.third,
                        maxHeight = 350.dp
                    )
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                TextLabel(
                    inputText = stringResource(id = R.string.total_d),
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.labelLarge
                )
                Spacer(modifier = Modifier.width(5.dp))
                TextLabel(
                    inputText = String.format(Locale.GERMANY, "%,.0f", totalKm),
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.labelLarge
                )
                TextLabel(
                    inputText = DistanceUnit.KM.name,
                    height = 30.dp,
                    textStyle = MaterialTheme.typography.labelLarge
                )
            }
        }
    }
}

@Composable
private fun Bar(
    bikeType: BikeType,
    value: Float,
    color: Color,
    maxHeight: Dp
) {
    val itemHeight = remember(value) { value * maxHeight.value / 15000 }
    val bikeModel = when (bikeType) {
        BikeType.ELECTRIC -> "E-Bike"
        BikeType.MTB -> "MTB"
        BikeType.ROAD_BIKE -> "Road"
        BikeType.HYBRID -> "City"
    }
    Box(
        modifier = Modifier
            .width(70.dp)
            .height(if (itemHeight > 50) itemHeight.dp else 70.dp),
        contentAlignment = Alignment.BottomCenter
    ) {
        Card(
            modifier = Modifier.padding(bottom = 40.dp),
            shape = RoundedCornerShape(5.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.onBackground
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(color = color),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextLabel(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(color = Color.Transparent),
                    inputText = String.format(Locale.GERMANY, "%,.0f", value),
                    textStyle = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold),
                    textColor = MaterialTheme.colorScheme.background
                )
            }
        }
        TextLabel(
            modifier = Modifier.background(color = Color.Transparent),
            inputText = bikeModel,
            height = 30.dp,
            textStyle = MaterialTheme.typography.titleMedium,
            textColor = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f)
        )
    }
}

@Preview()
@Composable
fun PreviewCustomBarChart() {
    CustomBarChart(
        totalKm = 25580.0,
        values = listOf(
            Triple(BikeType.ROAD_BIKE, 8500f, Color.Red),
            Triple(BikeType.MTB, 2650f, AppOrange),
            Triple(BikeType.HYBRID, 3420f, AppLightGreen),
            Triple(BikeType.ELECTRIC, 11000f, Color.White)
        )
    )
}
