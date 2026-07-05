package com.example.ui.theme

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

private val DarkColorScheme =
  darkColorScheme(
    primary = FireOrange,
    onPrimary = Color.White,
    secondary = FlameYellow,
    onSecondary = GunmetalBlack,
    tertiary = FireRed,
    background = GunmetalBlack,
    surface = SurfaceDark,
    onBackground = TextLight,
    onSurface = TextLight,
  )

private val LightColorScheme =
  darkColorScheme(
    primary = FireOrange,
    onPrimary = Color.White,
    secondary = FlameYellow,
    onSecondary = GunmetalBlack,
    tertiary = FireRed,
    background = GunmetalBlack,
    surface = SurfaceDark,
    onBackground = TextLight,
    onSurface = TextLight,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = true, // Force dark theme for gaming vibe
  dynamicColor: Boolean = false, // Disable dynamic colors to keep brand aesthetic
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else DarkColorScheme // Always premium dark

  MaterialTheme(colorScheme = colorScheme, typography = Typography, content = content)
}
