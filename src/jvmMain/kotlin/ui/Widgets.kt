package ui

import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.platform.LocalFocusManager

/**
 * Text box for editable text data.
 */
@Composable
fun TextDataField(initialData: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(initialData) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onChange(it)
        },
        modifier = modifier.onPreviewKeyEvent(getOnWidgetKeyEvent(LocalFocusManager.current))
    )
}

/**
 * Checkbox for boolean data.
 */
@Composable
fun CheckBox(initialData: Boolean, onChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf(initialData) }
    Checkbox(
        checked = value,
        onCheckedChange = {
            value = it
            onChange(it)
        },
        modifier = modifier.scale(1.5f).onPreviewKeyEvent(getOnWidgetKeyEvent(LocalFocusManager.current))
    )
}

/**
 * Text box that takes an integer as input. Used for integer data.
 */
@Composable
fun NumberPicker(initialData: Int, onChange: (Int) -> Unit, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(initialData.toString()) }
    TextField(
        value = text,
        isError = text.toIntOrNull() == null,
        onValueChange = { new ->
            text = new
            text.toIntOrNull()?.let { onChange(it) }
        },
        label = if (text.toIntOrNull() == null) {
            { Text("Must be an integer") }
        } else {
            null
        },
        modifier = modifier.onPreviewKeyEvent(getOnWidgetKeyEvent(LocalFocusManager.current))
    )
}
