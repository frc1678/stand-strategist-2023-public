package io

import androidx.compose.ui.awt.ComposeWindow
import io.files.Match
import io.files.MatchSchedule
import io.files.matchSchedule
import io.files.settings
import io.files.teamData
import io.files.timData
import io.github.evanrupert.excelkt.workbook
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import java.awt.FileDialog
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Opens a file selection dialog to select a match schedule file.
 * When a file is selected, it is moved to the config directory and the data is read to [matchSchedule].
 *
 * @param window The current window instance of the app.
 * @return The [MatchSchedule] object read from the selected file, or null if no file is selected.
 */
fun matchScheduleDialog(window: ComposeWindow): MatchSchedule? {
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
        return null
    }
    val matchSchedule: Map<String, Match>? =
        Json.decodeFromString(File(fileDialog.directory, fileDialog.file).readText())

    // Copy the file to the config directory
    File(fileDialog.directory, fileDialog.file).copyTo(MATCH_SCHEDULE_FILE)

    // Serialize the data
    return matchSchedule
}

/**
 * Opens a file selection dialog to select a place to export all the data.
 * Then combines the TIM data and team data into a single Excel workbook and writes it to the chosen file.
 *
 * @param window The current window instance of the app.
 */
fun saveDialog(window: ComposeWindow) {
    val fileDialog = FileDialog(window, "Select export location", FileDialog.SAVE).apply {
        // Only let the user select one file
        isMultipleMode = false
        // Set the default file name
        val dateTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm a"))
        file = "${settings!!.name} $dateTime.stand-strategist.xlsx"
        // Show the dialog
        isVisible = true
    }
    // The user clicked 'Cancel'
    if (fileDialog.file == null) return
    // Create an Excel workbook from the data and write to the file
    workbook {
        sheet("TIM Data (${settings!!.name})") {
            row { timData!!.first().keys.forEach { cell(it) } }
            timData!!.forEach {
                row { it.forEach { (_, value) -> cell(value) } }
            }
        }
        sheet("Team Data (${settings!!.name})") {
            row { teamData!!.first().keys.forEach { cell(it) } }
            teamData!!.forEach {
                row { it.forEach { (_, value) -> cell(value) } }
            }
        }
    }.xssfWorkbook.write(File(fileDialog.directory, fileDialog.file).outputStream())
}
