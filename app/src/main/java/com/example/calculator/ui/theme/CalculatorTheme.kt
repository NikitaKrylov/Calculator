package com.example.calculator.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

data class CalculatorColors(
    val primary: Color,
    val primaryText: Color,
    val primarySurface: Color,
    val background: Color,
    val secondary: Color,
    val secondaryText: Color,
    val secondarySurface: Color,
    val shadow: Color,

    val accentPrimary: Color,
    val accentSecondary: Color,
    val variantSurface: Color,
    val variantText: Color,

)


val lightPalette = CalculatorColors(
    primary = Color(0xFF38B9FF),
    secondary = Color(0xFF7CC9FF),
    background = Color(0xFFF7F8FB),
    primaryText = Color(0xFF424242),
    primarySurface = Color(0xFFFFFFFF),
    secondaryText = Color(0xFF818181),
    secondarySurface = Color(0xFFADE2FF),
    accentPrimary = Color(0xFF19ACFF),
    accentSecondary = Color(0xFF7CC9FF),
    shadow = Color(0xFFADE2FF),
    variantSurface = Color(0xFFFFFFFF),
    variantText = Color(0xFF858585)
)

val darkPalette = CalculatorColors(
    background = Color(0xFF17181A),
    primary = Color(0xFF29A8FF),
    secondary = Color(0xFF339DFF),
    primaryText = Color(0xFFFFFFFF),
    secondaryText = Color(0xFF818181),
    primarySurface = Color(0xFF303136),
    secondarySurface = Color(0xFF005DB2),
    variantSurface = Color(0xFF616161),
    accentSecondary = Color(0xFF1991FF),
    accentPrimary = Color(0xFF1991FF),
    variantText = Color(0xFFA5A5A5),
    shadow = Color(0xFF29A8FF)

)


object CalculatorTheme{
    val colors: CalculatorColors
        @Composable
        get() = LocalCalculatorColors.current
}

val LocalCalculatorColors = staticCompositionLocalOf<CalculatorColors>{
    error("No colors provided")
}

