package com.greedygame.brokenandroidcomposeproject.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Purple80, // Purple80
    secondary = Black_card, // PurpleGrey80
    tertiary = LigthGray, // Pink80
    background = Color(0xFF000000), // Pure black background
    surface = Color(0xFF625B71), // Dark gray surface
    onPrimary = Color.White, // White text on purple
    onSecondary = Color.Black, // Black text on purple-gray
    onTertiary = Color.Black, // Black text on pink
    onBackground = Color.White, // White text on black background
    onSurface = Color.Black // Black text on gray surface
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)


// Here we are just using only Dark color Scheme ,
// In future can add Dynamic color scheme. (For short project purpose only)
@Composable
fun ArticlesAppTheme(
    content: @Composable () -> Unit
) {
    // Directly use the DarkColorScheme, ignoring dynamicColor and darkTheme parameters
    MaterialTheme(
        colorScheme = DarkColorScheme, // Always use DarkColorScheme
        typography = Typography, // Ensure Typography is defined elsewhere
        content = content
    )
}