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

@Composable
fun TextDataField(initialData: String, onChange: (String) -> Unit, modifier: Modifier = Modifier) {
    var text by remember { mutableStateOf(initialData) }
    TextField(
        value = text,
        onValueChange = {
            text = it
            onChange(it)
        },
        modifier = modifier
    )
}

@Composable
fun CheckBox(initialData: Boolean, onChange: (Boolean) -> Unit, modifier: Modifier = Modifier) {
    var value by remember { mutableStateOf(initialData) }
    Checkbox(
        checked = value,
        onCheckedChange = {
            value = it
            onChange(it)
        },
        modifier = modifier
    )
}

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
        modifier = modifier
    )
}
