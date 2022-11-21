package io

import androidx.compose.ui.awt.ComposeWindow
import io.files.MatchSchedule
import io.files.matchSchedule
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.io.File

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

fun copyFileDialog(window: ComposeWindow) {
    val fileDialog = FileDialog(window, "Select a directory", FileDialog.SAVE).apply {
        isMultipleMode = false
        isVisible = true
    }

    if (fileDialog.file == null) {
        // The user clicked 'Cancel'
        return
    }

    TIM_DATA_FILE.copyTo(File(fileDialog.directory, fileDialog.file))
}
