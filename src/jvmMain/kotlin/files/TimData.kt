package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import io.TIM_DATA_FILE
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
 * The [ColumnAccessor]s for all the columns in the [timData].
 * Used to initially construct the empty [DataFrame] with the correct columns.
 */
private val timDataCols = mutableListOf<ColumnAccessor<Any?>>()

/**
 * Adds a [ColumnAccessor] to [timDataCols].
 *
 * @return The added [ColumnAccessor].
 */
private fun <T> ColumnAccessor<T>.register() = this.also { timDataCols.add(this) }

val matchNumber by column<Int>("Match Number").register()
val alliance by column<String>("Alliance").register()
val teamNumber by column<Int>("Team Number").register()
val defense by column<Boolean?>("Played Defense").register()
val defenseRating by column<Int?>("Defense Rating").register()
val shootingHub by column<Boolean?>("Shooting Hub").register()
val timeLeftToClimb by column<Int?>("Time Left to Climb").register()
val notes by column<String>("Notes").register()

/**
 * The main object storing all the Team-In-Match data.
 *
 * Don't directly make edits to this object, use [editTimData].
 */
var timData: AnyFrame? by mutableStateOf(null)

/**
 * Reads the team data from the [TEAM_DATA_FILE] into [teamData]. Creates a new [DataFrame] if the file doesn't exist.
 */
fun readTimData() {
    timData = if (TIM_DATA_FILE.exists()) {
        TIM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(timDataCols.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
    doneReadingTimData = true
}

var doneReadingTimData = false

/**
 * Applies [edit] to the [timData] and sets a new [DataFrame] so that recomposition gets triggered.
 *
 * @param edit The operation to be applied to the [timData].
 */
fun editTimData(edit: AnyFrame.() -> Unit) {
    timData = dataFrameOf(timData!!.columns()).apply(edit)
}

/**
 * The [DebouncedFileWriter] for the [timData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val timDataWriter = DebouncedFileWriter<AnyFrame>(TIM_DATA_FILE) { it.toCsv() }
