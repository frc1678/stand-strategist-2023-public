package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.named
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv

/**
 * The [ColumnAccessor]s for all the columns in the [teamData].
 * Used to initially construct the empty [DataFrame] with the correct columns.
 */
val teamDataCols = mutableListOf<ColumnAccessor<Any?>>()

/**
 * Adds a [ColumnAccessor] to [teamDataCols].
 *
 * @return The added [ColumnAccessor].
 */
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

/**
 * Reads the team data from the [TEAM_DATA_FILE] into [teamData]. Creates a new [DataFrame] if the file doesn't exist.
 */
fun readTeamData() {
    teamData = if (TEAM_DATA_FILE.exists()) {
        TEAM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(teamDataCols.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
    doneReadingTeamData = true
}

var doneReadingTeamData = false

/**
 * Applies [edit] to the [teamData] and sets a new [DataFrame] so that recomposition gets triggered.
 *
 * @param edit The operation to be applied to the [teamData].
 */
fun editTeamData(edit: AnyFrame.() -> Unit) {
    teamData = dataFrameOf(teamData!!.columns()).apply(edit)
}

/**
 * The [DebouncedFileWriter] for the [teamData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val teamDataWriter = DebouncedFileWriter<AnyFrame>(TEAM_DATA_FILE) { it.toCsv() }
