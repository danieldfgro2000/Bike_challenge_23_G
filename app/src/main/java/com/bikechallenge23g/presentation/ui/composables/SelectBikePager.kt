package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.data.model.enums.BikeColor
import com.bikechallenge23g.data.model.enums.BikeType
import com.bikechallenge23g.data.model.enums.BikeWheel
import com.bikechallenge23g.theme.AppBlue

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SelectBikePager(
    state: PagerState,
    currentWidth: Int,
    bikeTypes: Array<BikeType>,
    wheels: BikeWheel,
    bikeColor: BikeColor
) {
    var bikeType by remember { mutableStateOf(BikeType.ROAD_BIKE) }
    val pageSize = (currentWidth * 0.9).toInt()
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = state,
            pageSize = PageSize.Fixed(pageSize.dp)
        ) { page ->
            bikeType = bikeTypes[page]
            BikeCard(
                bikeType = bikeType,
                pageSize = pageSize,
                wheelSize = wheels,
                bikeColor = bikeColor
            )
        }
        Row(
            Modifier
                .height(20.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(bikeTypes.size) { iteration ->
                val color = if (state.currentPage == iteration) AppBlue else Color.White
                Box(
                    modifier = Modifier
                        .padding(5.dp)
                        .clip(CircleShape)
                        .background(color)
                        .size(10.dp)
                )
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Preview(showBackground = false)
@Composable
fun PreviewSelectBike() {
    val pagerState = rememberPagerState(
        initialPage = BikeType.ELECTRIC.ordinal,
        initialPageOffsetFraction = 0f
    ) { BikeType.values().size }

    SelectBikePager(
        state = pagerState,
        currentWidth = 900,
        bikeTypes = BikeType.values(),
        wheels = BikeWheel.BIG,
        bikeColor = BikeColor.BLUE
    )
}