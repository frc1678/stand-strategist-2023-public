@file:Suppress("UnusedReceiverParameter")

package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val StandStrategistLightColorScheme = lightColors(
    primary = Color(0xFF3D7AEB),
    primaryVariant = Color(0xFF3D7AEB),
    secondary = Color(0xFF3D7AEB),
    secondaryVariant = Color(0xFF3D7AEB),
    background = Color(0xFFEFF1F5),
    surface = Color(0xFFEFF1F5),
    error = Color(0xFFE64553),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black
)

val StandStrategistDarkColorScheme = darkColors(
    primary = Color(0xFF87B0F9),
    primaryVariant = Color(0xFF87B0F9),
    secondary = Color(0xFF87B0F9),
    secondaryVariant = Color(0xFF87B0F9),
    background = Color(0xFF1E1E2E),
    surface = Color(0xFF565970),
    error = Color(0xFFEBA0AC),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

val Colors.blueAlliance
    get() = Color(0xFF1F51FF)

val Colors.redAlliance
    get() = Color(0xFFFF6961)
