package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import io.MATCH_SCHEDULE_FILE
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.io.File

/**
 * Data class representing a match in the match schedule.
 */
@Serializable
data class Match(val teams: List<Team>)

/**
 * Data class representing a team within a match in the match schedule.
 */
@Serializable
data class Team(val number: Int, val color: String)

/**
 * Type that the match schedule is structured in.
 */
typealias MatchSchedule = Map<String, Match>

/**
 * The main match schedule object.
 * This shouldn't be modified from UI code; this will only be modified when a match schedule is imported.
 */
var matchSchedule: MatchSchedule? by mutableStateOf(null)

/**
 * Reads the match schedule from the [MATCH_SCHEDULE_FILE].
 * If there is no existing match schedule file, this opens a dialog window to select a match schedule file.
 *
 * @param window The current window instance of the app, used to open the dialog window.
 * @throws RuntimeException If the user clicks the 'Cancel' button in the file dialog instead of selecting a file.
 */
fun readMatchSchedule(window: ComposeWindow) {
    matchSchedule = if (MATCH_SCHEDULE_FILE.exists()) {
        Json.decodeFromString(MATCH_SCHEDULE_FILE.readText())
    } else {
        matchScheduleDialog(
            window,
            onCancel = { throw RuntimeException("Error: No match schedule file selected! Please select one.") }
        )
    }
}

/**
 * Opens a file selection dialog to select a match schedule file.
 * When a file is selected, it is moved to the config directory and the data is read to [matchSchedule].
 *
 * @param window The current window instance of the app.
 * @param onCancel Called if the user clicks the 'Cancel' button in the file dialog instead of selecting a file.
 * @return The [MatchSchedule] object read from the selected file, or null if no file is selected.
 */
fun matchScheduleDialog(window: ComposeWindow, onCancel: () -> Unit): MatchSchedule? {
    val fileDialog = FileDialog(window, "Select match schedule", FileDialog.LOAD).apply {
        // Only let the user select one file
        isMultipleMode = false
        // Make sure the user only selects .json files
        setFilenameFilter { _, name -> name.endsWith(".json") }
        // Show the dialog
        isVisible = true
    }
    if (fileDialog.file == null) {
        // The user clicked 'Cancel'
        onCancel()
        return null
    }
    // Copy the file to the config directory
    File(fileDialog.directory, fileDialog.file).copyTo(MATCH_SCHEDULE_FILE)
    // Serialize the data
    return Json.decodeFromString(File(fileDialog.directory, fileDialog.file).readText())
}
