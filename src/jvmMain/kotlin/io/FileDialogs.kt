package io

import androidx.compose.ui.awt.ComposeWindow
import io.files.MatchSchedule
import io.files.matchSchedule
import io.files.settings
import io.files.teamData
import io.files.timData
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.apache.poi.ss.usermodel.WorkbookFactory
import org.jetbrains.kotlinx.dataframe.io.writeExcel
import java.awt.FileDialog
import java.io.File
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

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
    // Create a new Excel workbook with all the data
    val wb = WorkbookFactory.create(true)
    timData!!.writeExcel(wb, sheetName = "TIM Data (${settings!!.name})")
    teamData!!.writeExcel(wb, sheetName = "Team Data (${settings!!.name})")
    // Write the Excel workbook to the chosen file
    wb.write(File(fileDialog.directory, fileDialog.file).outputStream())
    wb.close()
}
