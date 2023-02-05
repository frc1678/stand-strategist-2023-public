package ui.theme

import androidx.compose.material.Typography
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import io.files.editSettings

/**
 * The factor that every font size is multiplied by when zooming in, or divided by when zooming out.
 */
const val ZOOM_MULTIPLIER = 1.1f

/**
 * The initial [Typography] for the app. The actual typography can change depending on zoom level.
 */
private val DefaultTypography = Typography(
    defaultFontFamily = FontFamily(Font("font/Roboto-Flex-Variable.ttf")),
    h1 = TextStyle(fontSize = 120.sp),
    h2 = TextStyle(fontSize = 96.sp),
    h3 = TextStyle(fontSize = 48.sp),
    h4 = TextStyle(fontSize = 36.sp),
    h5 = TextStyle(fontSize = 28.sp),
    h6 = TextStyle(fontSize = 24.sp),
    subtitle1 = TextStyle(fontSize = 16.sp),
    subtitle2 = TextStyle(fontSize = 14.sp),
    body1 = TextStyle(fontSize = 20.sp),
    body2 = TextStyle(fontSize = 18.sp),
    button = TextStyle(fontSize = 28.sp, fontWeight = FontWeight.Medium),
    caption = TextStyle(fontSize = 14.sp),
    overline = TextStyle(fontSize = 12.sp)
)

/**
 * [Typography] used across the app. Can update based on zoom level.
 */
var CustomTypography by mutableStateOf(DefaultTypography)

/**
 * Updates the [CustomTypography] by applying the given [transform] to every font size.
 *
 * @param transform The transformation to apply to each font size. You may assume that the [TextUnit]s are in [sp].
 */
fun updateTypography(transform: (TextUnit) -> TextUnit) = with(CustomTypography) {
    CustomTypography = copy(
        h1 = h1.copy(fontSize = transform(h1.fontSize)),
        h2 = h2.copy(fontSize = transform(h2.fontSize)),
        h3 = h3.copy(fontSize = transform(h3.fontSize)),
        h4 = h4.copy(fontSize = transform(h4.fontSize)),
        h5 = h5.copy(fontSize = transform(h5.fontSize)),
        h6 = h6.copy(fontSize = transform(h6.fontSize)),
        subtitle1 = subtitle1.copy(fontSize = transform(subtitle1.fontSize)),
        subtitle2 = subtitle2.copy(fontSize = transform(subtitle2.fontSize)),
        body1 = body1.copy(fontSize = transform(body1.fontSize)),
        body2 = body2.copy(fontSize = transform(body2.fontSize)),
        button = button.copy(fontSize = transform(button.fontSize)),
        caption = caption.copy(fontSize = transform(caption.fontSize)),
        overline = overline.copy(fontSize = transform(overline.fontSize))
    )
}

/**
 * Multiplies all font sizes by the [ZOOM_MULTIPLIER].
 */
fun zoomIn() {
    editSettings { zoomLevel *= ZOOM_MULTIPLIER }
    updateTypography { it * ZOOM_MULTIPLIER }
}

/**
 * Divides all font sizes by the [ZOOM_MULTIPLIER]
 */
fun zoomOut() {
    editSettings { zoomLevel /= ZOOM_MULTIPLIER }
    updateTypography { it / ZOOM_MULTIPLIER }
}

/**
 * Resets all font sizes to the defaults.
 */
fun resetZoom() {
    editSettings { zoomLevel = 1.0f }
    CustomTypography = DefaultTypography
}
