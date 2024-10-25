package com.pedrogomez.pokeapp.core.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    )
)

val Typography.pokeLabelSmall: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = labelSfontSize,
            lineHeight = labelSlineHeight,
            fontWeight = FontWeight.Normal,
        )
    }

val Typography.pokeLabelMedium: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = labelMfontSize,
            lineHeight = labelMlineHeight,
            fontWeight = FontWeight.Bold,
        )
    }

val Typography.pokeLabelLarge: TextStyle
    @Composable
    get() {
        return TextStyle(
            fontSize = labelLfontSize,
            lineHeight = labelLlineHeight,
            fontWeight = FontWeight.Bold,
        )
    }

val labelSfontSize = 12.sp
val labelSlineHeight = 14.sp
val labelMfontSize = 16.sp
val labelMlineHeight = 18.sp
val labelLfontSize = 20.sp
val labelLlineHeight = 22.sp