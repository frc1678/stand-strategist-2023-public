package files

import Screens
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
data class Settings(
    var match: Int = 1,
    var alliance: String = "blue",
    var name: String = "Unnamed",
    var screen: Screens = Screens.STARTING
)

var settings: Settings? by mutableStateOf(null)

fun readSettings() {
    settings = if (SETTINGS_FILE.exists()) {
        Json.decodeFromString(SETTINGS_FILE.readText())
    } else {
        Settings()
    }
    doneReadingSettings = true
}

var doneReadingSettings = false

fun editSettings(edit: Settings.() -> Unit) {
    settings = settings!!.copy().apply(edit)
}

val settingsWriter = DebouncedFileWriter<Settings>(SETTINGS_FILE) {
    Json.encodeToString(it)
}
