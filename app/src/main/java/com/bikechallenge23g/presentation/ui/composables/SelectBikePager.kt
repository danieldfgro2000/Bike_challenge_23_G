package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.data.model.enums.BikeColors
import com.bikechallenge23g.data.model.enums.BikeTypes
import com.bikechallenge23g.data.model.enums.BikeWheels

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectBikePager(
    bikeTypes: Array<BikeTypes>,
    wheels: BikeWheels,
    bikeColors: BikeColors,
    defaultBikeType: BikeTypes,
    onBikeTypeSelected: (BikeTypes) -> Unit
) {
    val pagerState = rememberPagerState(
        initialPage = defaultBikeType.ordinal,
        initialPageOffsetFraction = -0.16f
    ) { bikeTypes.size }

    var bikeType by remember { mutableStateOf(BikeTypes.ROAD_BIKE) }

    HorizontalPager(
        state = pagerState,
        pageSpacing = 20.dp,
        pageSize = PageSize.Fixed(300.dp)
    ) { page ->
        bikeType = bikeTypes[page]
        onBikeTypeSelected(bikeType)
        BikeCard(
            bikeTypes = bikeType,
            wheelSize = wheels,
            bikeColor = bikeColors
        )
    }
}

@Preview(showBackground = false)
@Composable
fun PreviewSelectBike() {
    SelectBikePager(
        bikeTypes = BikeTypes.values(),
        wheels = BikeWheels.BIG,
        bikeColors = BikeColors.BLUE,
        defaultBikeType = BikeTypes.ELECTRIC
    ) {}
}