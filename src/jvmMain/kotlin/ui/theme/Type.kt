package ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.sp

val StandStrategistTypography = Typography(
    defaultFontFamily = FontFamily(Font("font/Roboto-Flex-Variable.ttf")),
    h1 = TextStyle(fontSize = 120.sp),
    h2 = TextStyle(fontSize = 96.sp),
    h3 = TextStyle(fontSize = 48.sp),
    h4 = TextStyle(fontSize = 36.sp),
    h5 = TextStyle(fontSize = 24.sp),
    h6 = TextStyle(fontSize = 20.sp, fontWeight = FontWeight.Medium),
    subtitle1 = TextStyle(fontSize = 16.sp),
    subtitle2 = TextStyle(fontSize = 14.sp),
    body1 = TextStyle(fontSize = 20.sp),
    body2 = TextStyle(fontSize = 18.sp),
    button = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Medium),
    caption = TextStyle(fontSize = 14.sp),
    overline = TextStyle(fontSize = 12.sp)
)
