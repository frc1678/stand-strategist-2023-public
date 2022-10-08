package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

@Serializable
data class Settings(val lastMatch: Int)

var settings: Settings? by mutableStateOf(null)

fun readSettings() {
    settings = if (SETTINGS_FILE.exists()) {
        Json.decodeFromString(SETTINGS_FILE.readText())
    } else {
        Settings(1)
    }
}
