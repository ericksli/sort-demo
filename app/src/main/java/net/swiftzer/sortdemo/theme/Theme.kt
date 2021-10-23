package net.swiftzer.sortdemo.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightThemeColors = lightColors(
    primary = Grey600,
    primaryVariant = Grey800,
    onPrimary = Color.White,
    secondary = Teal200,
    secondaryVariant = Teal700,
    onSecondary = Color.Black,
)

private val DarkThemeColors = darkColors(
    primary = Grey300,
    primaryVariant = Grey800,
    onPrimary = Color.Black,
    secondary = Teal200,
    secondaryVariant = Teal200,
    onSecondary = Color.Black,
)


@Composable
fun AppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        typography = Typography(),
        shapes = Shapes(),
        content = content
    )
}
