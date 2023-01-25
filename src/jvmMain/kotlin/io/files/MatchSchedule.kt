package io.files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.awt.ComposeWindow
import io.MATCH_SCHEDULE_FILE
import io.matchScheduleDialog
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

/**
 * Data class representing a match in the match schedule.
 */
@Serializable
data class Match(val teams: List<Team>)

/**
 * Data class representing a team within a match in the match schedule.
 */
@Serializable
data class Team(val number: String, val color: String)

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
 */
fun readMatchSchedule(window: ComposeWindow) {
    if (MATCH_SCHEDULE_FILE.exists()) {
        matchSchedule = Json.decodeFromString(MATCH_SCHEDULE_FILE.readText())
    } else {
        matchScheduleDialog(window)?.also { matchSchedule = it }
    }
}
