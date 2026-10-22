package com.example.ui.theme

enum class ThemeMode {
    LIGHT,
    DARK,
    SYSTEM
}

enum class ColorPalette(val displayName: String, val description: String) {
    NAVY("University Navy", "Classic Oxford blue & academic gold"),
    EMERALD("Malawian Emerald", "Acacia sun & vibrant Malawian green"),
    AMBER("Sunset Amber", "Warm terracotta & parchment paper"),
    MIDNIGHT("Midnight Scholar", "Deep slate & electric indigo")
}

data class ThemeSettings(
    val mode: ThemeMode = ThemeMode.SYSTEM,
    val palette: ColorPalette = ColorPalette.NAVY
)
