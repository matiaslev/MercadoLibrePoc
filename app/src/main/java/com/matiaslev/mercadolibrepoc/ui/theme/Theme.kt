package com.matiaslev.mercadolibrepoc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = yellow,
    primaryVariant = yellow,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = yellow,
    primaryVariant = darkYellow,
    secondary = Color.Blue,

    // Other default colors to override
    background = grey,
    surface = Color.Red,
    onPrimary = Color.Black,
    onSecondary = Color.Red,
    onBackground = Color.Black,
    onSurface = Color.DarkGray
)

@Composable
fun MercadoLibrePocTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
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
}