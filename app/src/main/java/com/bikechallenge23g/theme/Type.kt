package com.bikechallenge23g.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.bikechallenge23g.R

// Set of Material typography styles to start with
val robotoNormal = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.robotoregular,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        )
    )
)
val robotoSemi = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.robotomedium,
            weight = FontWeight.Normal,
            style = FontStyle.Normal
        )
    )
)
val robotoThin = FontFamily(
    fonts = listOf(
        Font(
            resId = R.font.robotolight,
            weight = FontWeight.Light,
            style = FontStyle.Normal
        )
    )
)

val Typography = Typography(

    headlineLarge = TextStyle(
        fontFamily = robotoSemi,
        fontWeight = FontWeight.Bold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    headlineMedium = TextStyle(
        fontFamily = robotoSemi,
        fontWeight = FontWeight.Bold,
        fontSize = 17.sp,
        lineHeight = 20.sp,
    ),
    headlineSmall = TextStyle(
        fontFamily = robotoSemi,
        fontWeight = FontWeight.Bold,
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
    titleLarge = TextStyle(
        fontFamily = robotoSemi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 24.sp,
    ),
    titleMedium = TextStyle(
        fontFamily = robotoSemi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 17.sp,
        lineHeight = 20.sp,
    ),
    titleSmall = TextStyle(
        fontFamily = robotoSemi,
        fontWeight = FontWeight.SemiBold,
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),

    bodyLarge = TextStyle(
        fontFamily = robotoNormal,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 24.sp,

        ),
    bodyMedium = TextStyle(
        fontFamily = robotoNormal,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 20.sp,

        ),
    bodySmall = TextStyle(
        fontFamily = robotoNormal,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 16.sp,

        ),
    labelLarge = TextStyle(
        fontFamily = robotoNormal,
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 22.sp,
    ),

    labelMedium = TextStyle(
        fontFamily = robotoNormal,
        fontWeight = FontWeight.Normal,
        fontSize = 17.sp,
        lineHeight = 22.sp,
    ),
    labelSmall = TextStyle(
        fontFamily = robotoThin,
        fontWeight = FontWeight.Normal,
        fontSize = 13.sp,
        lineHeight = 16.sp,
    ),
)


/* Other default text styles to override
titleLarge = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Normal,
fontSize = 22.sp,
lineHeight = 28.sp,
letterSpacing = 0.sp
),
labelSmall = TextStyle(
fontFamily = FontFamily.Default,
fontWeight = FontWeight.Medium,
fontSize = 11.sp,
lineHeight = 16.sp,
letterSpacing = 0.5.sp
)
*/
