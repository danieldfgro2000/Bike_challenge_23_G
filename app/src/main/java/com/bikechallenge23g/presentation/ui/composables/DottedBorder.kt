package com.bikechallenge23g.presentation.ui.composables

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

fun Modifier.dottedBorder(
    color: Color,
    strokeWidth: Dp,
    gapSize: Int,
    phase: Float,
    cornerRadius: Dp = 0.dp
) = this.then(
    object : DrawModifier {
        override fun ContentDrawScope.draw() {
            drawContent()
            val style = Stroke(
                width = strokeWidth.toPx(),
                pathEffect = PathEffect.dashPathEffect(
                    floatArrayOf(gapSize.toFloat(), gapSize.toFloat()), phase
                )
            )
            drawRoundRect(
                color = color,
                style = style,
                cornerRadius = CornerRadius(cornerRadius.toPx())
            )
        }
    }
)