package io.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.SETTINGS_FILE
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import ui.navigation.Destination
import ui.navigation.NavGraph

/**
 * Data class used to represent the app's current settings.
 */
@Serializable
data class Settings(
    var match: Int = 1,
    var alliance: String = "blue",
    var name: String = System.getProperty("user.name"),
    var screen: Destination = NavGraph.STARTING.destination,
    var darkTheme: Boolean = true
)

/**
 * The main settings object. Do not edit this directly, use [editSettings].
 */
var settings: Settings? by mutableStateOf(null)

/**
 * Reads from the [SETTINGS_FILE] into [settings]. Constructs a new [Settings] object if the file doesn't exist.
 */
fun readSettings() {
    settings = if (SETTINGS_FILE.exists()) {
        Json.decodeFromString(SETTINGS_FILE.readText())
    } else {
        Settings()
    }
    doneReadingSettings = true
}

var doneReadingSettings = false

/**
 * Applies [edit] to the [settings] and sets a new instance of [Settings] so that recomposition gets triggered.
 *
 * @param edit The operation to be applied to the [settings].
 */
fun editSettings(edit: Settings.() -> Unit) {
    settings = settings!!.copy().apply(edit)
}

/**
 * The [DebouncedFileWriter] for the [settings].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val settingsWriter = DebouncedFileWriter<Settings>(SETTINGS_FILE) {
    Json.encodeToString(it)
}
