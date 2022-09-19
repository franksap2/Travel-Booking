package com.franksap2.travelbooking.core.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Grey100,
    onPrimary = Color.Black,
    primaryVariant = Purple700,
    secondary = Orange700
)

private val LightColorPalette = lightColors(
    primary = Color.Black,
    onPrimary = Color.White,
    primaryVariant = Purple700,
    secondary = Orange700
)

@Composable
fun TravelBookingTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )

    val systemUi = rememberSystemUiController()
    SideEffect {
        systemUi.systemBarsDarkContentEnabled = !darkTheme
    }

}