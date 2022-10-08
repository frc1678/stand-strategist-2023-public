package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.SETTINGS_FILE
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class Settings(var lastMatch: Int)

var settings: Settings? by mutableStateOf(null)

fun readSettings() {
    settings = if (SETTINGS_FILE.exists()) {
        Json.decodeFromString(SETTINGS_FILE.readText())
    } else {
        Settings(1)
    }
}

val settingsWriter = DebouncedFileWriter<Settings>(SETTINGS_FILE) {
    Json.encodeToString(it)
}
