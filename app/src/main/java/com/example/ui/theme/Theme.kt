package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

fun getCustomColorScheme(settings: ThemeSettings, isSystemDark: Boolean): ColorScheme {
    val isDark = when (settings.mode) {
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
        ThemeMode.SYSTEM -> isSystemDark
    }

    return when (settings.palette) {
        ColorPalette.NAVY -> {
            if (isDark) {
                darkColorScheme(
                    primary = NavyPrimaryDark,
                    onPrimary = Color(0xFF0F172A),
                    primaryContainer = Color(0xFF1E293B),
                    onPrimaryContainer = NavyPrimaryDark,
                    secondary = NavySecondaryDark,
                    onSecondary = Color(0xFF0F172A),
                    secondaryContainer = Color(0xFF382A0F),
                    onSecondaryContainer = NavySecondaryDark,
                    tertiary = NavyTertiaryDark,
                    background = NavyBgDark,
                    onBackground = Color(0xFFF1F5F9),
                    surface = NavySurfaceDark,
                    onSurface = Color(0xFFF1F5F9),
                    surfaceVariant = Color(0xFF1E293B),
                    onSurfaceVariant = Color(0xFFCBD5E1)
                )
            } else {
                lightColorScheme(
                    primary = NavyPrimaryLight,
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFDBEAFE),
                    onPrimaryContainer = NavyPrimaryLight,
                    secondary = NavySecondaryLight,
                    onSecondary = Color.White,
                    secondaryContainer = Color(0xFFFEF3C7),
                    onSecondaryContainer = NavySecondaryLight,
                    tertiary = NavyTertiaryLight,
                    background = NavyBgLight,
                    onBackground = Color(0xFF0F172A),
                    surface = NavySurfaceLight,
                    onSurface = Color(0xFF0F172A),
                    surfaceVariant = Color(0xFFF1F5F9),
                    onSurfaceVariant = Color(0xFF334155)
                )
            }
        }
        ColorPalette.EMERALD -> {
            if (isDark) {
                darkColorScheme(
                    primary = EmeraldPrimaryDark,
                    onPrimary = Color(0xFF022C22),
                    primaryContainer = Color(0xFF064E3B),
                    onPrimaryContainer = EmeraldPrimaryDark,
                    secondary = EmeraldSecondaryDark,
                    onSecondary = Color(0xFF022C22),
                    secondaryContainer = Color(0xFF3B2E0A),
                    onSecondaryContainer = EmeraldSecondaryDark,
                    tertiary = EmeraldTertiaryDark,
                    background = EmeraldBgDark,
                    onBackground = Color(0xFFECFDF5),
                    surface = EmeraldSurfaceDark,
                    onSurface = Color(0xFFECFDF5),
                    surfaceVariant = Color(0xFF133E32),
                    onSurfaceVariant = Color(0xFFA7F3D0)
                )
            } else {
                lightColorScheme(
                    primary = EmeraldPrimaryLight,
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFD1FAE5),
                    onPrimaryContainer = EmeraldPrimaryLight,
                    secondary = EmeraldSecondaryLight,
                    onSecondary = Color.White,
                    secondaryContainer = Color(0xFFFEF3C7),
                    onSecondaryContainer = EmeraldSecondaryLight,
                    tertiary = EmeraldTertiaryLight,
                    background = EmeraldBgLight,
                    onBackground = Color(0xFF022C22),
                    surface = EmeraldSurfaceLight,
                    onSurface = Color(0xFF022C22),
                    surfaceVariant = Color(0xFFE6F4EA),
                    onSurfaceVariant = Color(0xFF1E3A2F)
                )
            }
        }
        ColorPalette.AMBER -> {
            if (isDark) {
                darkColorScheme(
                    primary = AmberPrimaryDark,
                    onPrimary = Color(0xFF291800),
                    primaryContainer = Color(0xFF452600),
                    onPrimaryContainer = AmberPrimaryDark,
                    secondary = AmberSecondaryDark,
                    onSecondary = Color(0xFF291800),
                    secondaryContainer = Color(0xFF2E2248),
                    onSecondaryContainer = AmberSecondaryDark,
                    tertiary = AmberTertiaryDark,
                    background = AmberBgDark,
                    onBackground = Color(0xFFFFFBEB),
                    surface = AmberSurfaceDark,
                    onSurface = Color(0xFFFFFBEB),
                    surfaceVariant = Color(0xFF382914),
                    onSurfaceVariant = Color(0xFFFDE68A)
                )
            } else {
                lightColorScheme(
                    primary = AmberPrimaryLight,
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFFED7AA),
                    onPrimaryContainer = Color(0xFF7C2D12),
                    secondary = AmberSecondaryLight,
                    onSecondary = Color.White,
                    secondaryContainer = Color(0xFFE0E7FF),
                    onSecondaryContainer = AmberSecondaryLight,
                    tertiary = AmberTertiaryLight,
                    background = AmberBgLight,
                    onBackground = Color(0xFF2E1B0F),
                    surface = AmberSurfaceLight,
                    onSurface = Color(0xFF2E1B0F),
                    surfaceVariant = Color(0xFFF4EDE4),
                    onSurfaceVariant = Color(0xFF523B28)
                )
            }
        }
        ColorPalette.MIDNIGHT -> {
            if (isDark) {
                darkColorScheme(
                    primary = MidnightPrimaryDark,
                    onPrimary = Color(0xFF1E1B4B),
                    primaryContainer = Color(0xFF312E81),
                    onPrimaryContainer = MidnightPrimaryDark,
                    secondary = MidnightSecondaryDark,
                    onSecondary = Color(0xFF1E1B4B),
                    secondaryContainer = Color(0xFF3B2E0A),
                    onSecondaryContainer = MidnightSecondaryDark,
                    tertiary = MidnightTertiaryDark,
                    background = MidnightBgDark,
                    onBackground = Color(0xFFF5F3FF),
                    surface = MidnightSurfaceDark,
                    onSurface = Color(0xFFF5F3FF),
                    surfaceVariant = Color(0xFF1E2438),
                    onSurfaceVariant = Color(0xFFC7D2FE)
                )
            } else {
                lightColorScheme(
                    primary = MidnightPrimaryLight,
                    onPrimary = Color.White,
                    primaryContainer = Color(0xFFE0E7FF),
                    onPrimaryContainer = MidnightPrimaryLight,
                    secondary = MidnightSecondaryLight,
                    onSecondary = Color.White,
                    secondaryContainer = Color(0xFFFEF3C7),
                    onSecondaryContainer = MidnightSecondaryLight,
                    tertiary = MidnightTertiaryLight,
                    background = MidnightBgLight,
                    onBackground = Color(0xFF1E1B4B),
                    surface = MidnightSurfaceLight,
                    onSurface = Color(0xFF1E1B4B),
                    surfaceVariant = Color(0xFFECEBFA),
                    onSurfaceVariant = Color(0xFF3730A3)
                )
            }
        }
    }
}

@Composable
fun MyApplicationTheme(
    settings: ThemeSettings = ThemeSettings(),
    content: @Composable () -> Unit,
) {
    val isSystemDark = isSystemInDarkTheme()
    val colorScheme = getCustomColorScheme(settings, isSystemDark)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
