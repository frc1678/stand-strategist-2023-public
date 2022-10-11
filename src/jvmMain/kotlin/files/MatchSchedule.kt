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

@Serializable
data class Match(val teams: List<Team>)

@Serializable
data class Team(val number: Int, val color: String)

typealias MatchSchedule = Map<String, Match>?

var matchSchedule: MatchSchedule by mutableStateOf(null)

fun readMatchSchedule(window: ComposeWindow) {
    matchSchedule = if (MATCH_SCHEDULE_FILE.exists()) {
        Json.decodeFromString(MATCH_SCHEDULE_FILE.readText())
    } else {
        matchScheduleDialog(window)
    }
}

fun matchScheduleDialog(window: ComposeWindow): MatchSchedule {
    val fileDialog = FileDialog(window, "Select match schedule", FileDialog.LOAD).apply {
        isMultipleMode = false
        setFilenameFilter { _, name -> name.endsWith(".json") }
        isVisible = true
    }
    if (fileDialog.file == null) throw RuntimeException("Error: No match schedule file selected! Please select one.")
    File(fileDialog.directory, fileDialog.file).copyTo(MATCH_SCHEDULE_FILE)
    return Json.decodeFromString(MATCH_SCHEDULE_FILE.readText())
}
