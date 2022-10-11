package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.named
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv

private val teamDataCols = mutableListOf<ColumnAccessor<Any?>>()

private fun <T> ColumnAccessor<T>.register() = this.also { teamDataCols.add(this) }

val team by column<String>("Team").register()
val competence by column<String>("Driving and Scoring Competence").register()
val strengthsAndWeaknesses by column<String>("Strengths/Weaknesses").register()
val defensiveMethod by column<String>("Defensive Method").register()
val extraNotes by column<String>("Notes").register()

/**
 * The main object storing all the team data.
 *
 * Don't directly make edits to this object, use [editTeamData].
 */
var teamData: AnyFrame? by mutableStateOf(null)

fun readTeamData() {
    teamData = if (TEAM_DATA_FILE.exists()) {
        TEAM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(teamDataCols.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
    doneReadingTeamData = true
}

var doneReadingTeamData = false

fun editTeamData(edit: AnyFrame.() -> Unit) {
    teamData = dataFrameOf(teamData!!.columns()).apply(edit)
}

val teamDataWriter = DebouncedFileWriter<AnyFrame>(TEAM_DATA_FILE) { it.toCsv() }
