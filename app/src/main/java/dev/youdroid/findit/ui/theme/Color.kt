package dev.youdroid.findit.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color


val LightPrimaryColor = Color(0xFF30336B)
val LightPrimaryDarkColor = Color(0xFF130F40)
val LightAccentColor = Color(0xFFF9CA24)

val DarkPrimaryColor = Color(0xFF30336B)
val DarkPrimaryDarkColor = Color(0xFF130F40)
val DarkAccentColor = Color(0xFFF9CA24)


val LightThemeColors = lightColorScheme(
    primary = LightPrimaryColor,
    inversePrimary = LightPrimaryDarkColor,
    secondary = LightAccentColor,
    background = Color.White,
    surface = Color.White,
    onSecondary = LightPrimaryDarkColor,
    onBackground = LightPrimaryDarkColor,
    onSurface = LightPrimaryDarkColor,
    onSecondaryContainer = Color.White,

)

val DarkThemeColors = darkColorScheme(
    primary = DarkPrimaryColor,
    inversePrimary = DarkPrimaryDarkColor,
    onPrimary = Color.White,
    secondary = DarkAccentColor,
    background = DarkPrimaryDarkColor,
    surface = DarkPrimaryDarkColor,
    onSecondary = DarkPrimaryDarkColor,
    onBackground = Color.White,
    onSurface = Color.White,
    onSecondaryContainer = Color.White
)