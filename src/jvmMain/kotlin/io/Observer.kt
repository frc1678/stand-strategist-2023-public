package io

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import io.files.doneReadingSettings
import io.files.doneReadingTimData
import io.files.settings
import io.files.settingsWriter
import io.files.teamDataWriter
import io.files.timData
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
        for (writer in listOf(settingsWriter, timDataWriter, teamDataWriter)) {
            launch(Dispatchers.IO) {
                writer.start()
            }
        }
    }
    // When one of the data holders is updated, write the data to its file.
    LaunchedEffect(settings) {
        if (doneReadingSettings && settings != null) settingsWriter.writeData(settings!!)
    }
    LaunchedEffect(timData) {
        if (doneReadingTimData && timData != null) timDataWriter.writeData(timData!!)
    }
}
