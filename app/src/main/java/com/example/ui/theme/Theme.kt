package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val SkDarkColorScheme = darkColorScheme(
    primary = ElectricBlue,
    onPrimary = TextPrimary,
    primaryContainer = NavySurfaceElevated,
    onPrimaryContainer = NeonCyan,
    secondary = NeonCyan,
    onSecondary = ObsidianBlack,
    secondaryContainer = ChipBackground,
    onSecondaryContainer = TextPrimary,
    tertiary = ElectricBlueLight,
    onTertiary = ObsidianBlack,
    background = ObsidianBlack,
    onBackground = TextPrimary,
    surface = DeepNavySurface,
    onSurface = TextPrimary,
    surfaceVariant = NavySurfaceCard,
    onSurfaceVariant = TextSecondary,
    outline = NavySurfaceBorder,
    outlineVariant = ChipBorder,
    error = AccentRed,
    onError = TextPrimary
)

@Composable
fun MyApplicationTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = SkDarkColorScheme,
        typography = Typography,
        content = content
    )
}
