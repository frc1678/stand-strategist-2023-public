@file:Suppress("UnusedReceiverParameter")

package ui.theme

import androidx.compose.material.Colors
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.ui.graphics.Color

val StandStrategistLightColorScheme = lightColors(
    primary = Color(0xFF4078F2),
    primaryVariant = Color(0xFF4885E4),
    secondary = Color(0xFF50A14F),
    secondaryVariant = Color(0xFF2FA67F),
    background = Color(0xFFFAFAFA),
    surface = Color(0xFFF0F0F0),
    error = Color(0xFFE45649),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black,
    onError = Color.Black
)

val StandStrategistDarkColorScheme = darkColors(
    primary = Color(0xFF61AFEF),
    primaryVariant = Color(0xFF7AAFE5),
    secondary = Color(0xFF98C379),
    secondaryVariant = Color(0xFF75C5A0),
    background = Color(0xFF21252B),
    surface = Color(0xFF282C34),
    error = Color(0xFFF44747),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White,
    onError = Color.Black
)

val Colors.blueAlliance
    get() = Color(0xFF1F51FF)

val Colors.onBlueAlliance
    get() = if (isLight) onPrimary else onSurface

val Colors.redAlliance
    get() = Color(0xFFFF6961)

val Colors.onRedAlliance
    get() = if (isLight) onSurface else onPrimary
