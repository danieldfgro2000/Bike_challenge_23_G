package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.bikechallenge23g.R

@Composable
fun CustomSlider(
    modifier: Modifier = Modifier,
    progress: Float,
    range: Float
) {
    var boxWidth by remember { mutableIntStateOf(0) }
    val posStart = progress / range
    val posEnd = 1 - posStart
    val sPE = if (posEnd == 0f) 0.1f else posEnd
    val sBW = if (boxWidth == 0) 1 else boxWidth

    val weightStart: Float = 1 / (sBW / posStart)
    val weightEnd: Float = 1 / (sBW / sPE)
    val sWE = if (progress > range) 0.99f else if (weightEnd <= 0.0f) 0.001f else weightEnd
    val sWs = if (progress > range) 0.01f else if (weightStart <= 0.0f) 0.001f else weightStart

    Box(
        modifier = modifier
            .onGloballyPositioned { layoutCoordinates ->
                boxWidth = layoutCoordinates.size.width
            },
        contentAlignment = Alignment.CenterStart

    ) {
        Image(
            painter = painterResource(id = R.drawable.loading_circle),
            contentDescription = null
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading_over),
                contentDescription = null,
                modifier = Modifier.weight(sWs),
                contentScale = ContentScale.Fit
            )
            Image(
                painter = painterResource(id = R.drawable.loading_wrench),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.loading_bar),
                contentDescription = null,
                modifier = Modifier.weight(sWE),
                contentScale = ContentScale.Fit
            )
        }

        Image(
            painter = painterResource(id = R.drawable.loading_bolt),
            contentDescription = null,
            modifier = Modifier.align(
                Alignment.CenterEnd
            )
        )

        Slider(
            value = progress,
            onValueChange = { },
            valueRange = 0f..range,
            modifier = Modifier
                .align(Alignment.Center)
                .fillMaxWidth()
                .alpha(0f)
        )
    }
}


@Preview
@Composable
fun PreviewCustomSlidebar20() {
    CustomSlider(Modifier, 20f, 100f)
}

@Preview
@Composable
fun PreviewCustomSlidebar70() {
    CustomSlider(Modifier, 70f, 100f)
}

@Preview
@Composable
fun PreviewCustomSlidebar200() {
    CustomSlider(Modifier, 0f, 1f)
}

@Preview
@Composable
fun PreviewCustomSlidebarNeg() {
    CustomSlider(Modifier, 100f, 10f)
}
