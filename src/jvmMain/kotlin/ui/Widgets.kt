package ui

import androidx.compose.material.Checkbox
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
    var text by remember { mutableStateOf(initialData)}
    Checkbox(
        checked = text,
        onCheckedChange = {
            text = it
            onChange(it)
        },
        modifier = modifier
    )
}
