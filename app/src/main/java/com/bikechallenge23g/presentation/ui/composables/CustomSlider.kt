package com.bikechallenge23g.presentation.ui.composables


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bikechallenge23g.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@Composable
fun CustomSlider(
    progress: Float,
    range: Float
) {
    var sWs by remember { mutableFloatStateOf(0.001f) }
    var sWE by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(progress, range) {
        withContext(Dispatchers.Default) {

            val clampedProgress = progress.coerceIn(0.001f, range)
            val posStart = clampedProgress / range
            val posEnd = 1f - posStart

            withContext(Dispatchers.Main) {
                sWs = posStart
                sWE = if (posEnd > 0f) posEnd else 0.001f
            }
        }
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxWidth(),
        contentAlignment = Alignment.CenterStart
    ) {
        Image(
            modifier = Modifier.align(Alignment.CenterStart),
            painter = painterResource(id = R.drawable.loading_circle),
            contentDescription = null
        )
        Row(
            modifier = Modifier.padding(start = 5.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.loading_over),
                contentDescription = null,
                modifier = Modifier.weight(sWs),
                contentScale = ContentScale.FillWidth
            )
            Image(
                painter = painterResource(id = R.drawable.loading_wrench),
                contentDescription = null
            )
            Image(
                painter = painterResource(id = R.drawable.loading_bar),
                contentDescription = null,
                modifier = Modifier.weight(sWE),
                contentScale = ContentScale.FillWidth
            )
        }
        Image(
            modifier = Modifier.align(Alignment.CenterEnd),
            painter = painterResource(id = R.drawable.loading_bolt),
            contentDescription = null
        )
    }
}


@Preview
@Composable
fun PreviewCustomSlidebar20() {
    CustomSlider(399f, 400f)
}

@Preview
@Composable
fun PreviewCustomSlidebar70() {
    CustomSlider(88f, 100f)
}

@Preview
@Composable
fun PreviewCustomSlidebar200() {
    CustomSlider(0f, 1f)
}

@Preview
@Composable
fun PreviewCustomSlidebarNeg() {
    CustomSlider(100f, 10f)
}
