package io

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.files.doneReadingSettings
import io.files.matchSchedule
import io.files.matchScheduleWriter
import io.files.settings
import io.files.settingsWriter
import io.files.teamDataWriter
import io.files.timDataWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Composable that launches all the background tasks for writing to files.
 */
@Composable
fun Observer() {
    // Starts every file writer when the app starts.
    LaunchedEffect(true) {
        for (writer in listOf(settingsWriter, matchScheduleWriter, timDataWriter, teamDataWriter)) {
            launch(Dispatchers.IO) {
                writer.start()
            }
        }
    }
    // When the settings data holder is updated, write the data to its file.
    LaunchedEffect(settings) {
        if (doneReadingSettings && settings != null) settingsWriter.writeData(settings!!)
    }
    LaunchedEffect(matchSchedule) {
        if (matchSchedule != null) matchScheduleWriter.writeData(matchSchedule!!)
    }
}
