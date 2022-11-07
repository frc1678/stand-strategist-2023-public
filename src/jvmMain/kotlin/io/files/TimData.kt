package io.files

import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import io.TIM_DATA_FILE
import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.named
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv

/**
 * [ColumnAccessor] for accessing the 'Match Number' column. All other columns should be accessed through [timDataCols].
 */
val match by column<Int>("Match Number")

/**
 * [ColumnAccessor] for accessing the 'Alliance' column. All other columns should be accessed through [timDataCols].
 */
val alliance by column<String>("Alliance")

/**
 * The [ColumnAccessor]s for all the columns in the [timData].
 * Used to initially construct the empty [DataFrame] with the correct columns.
 */
val timDataCols = mutableListOf<ColumnAccessor<Any?>>(
    match,
    alliance,
    team,
    column<Boolean?>("Played Defense"),
    column<Int?>("Defense Rating"),
    column<Boolean?>("Shooting Hub"),
    column<Int?>("Time Left to Climb"),
    column<String>("Notes")
)

/**
 * The main object storing all the Team-In-Match data.
 *
 * To update the data, use [DataFrame.update]:
 * ```kt
 * timData = timData!!.update(/* column */)
 *     .where { /* row condition */ }
 *      .with { /* new value */ }
 * ```
 */
var timData: AnyFrame? = null
    set(value) {
        field = value
        runBlocking { timDataWriter.writeData(value!!) }
    }

/**
 * Reads the team data from the [TEAM_DATA_FILE] into [teamData]. Creates a new [DataFrame] if the file doesn't exist.
 */
fun readTimData() {
    timData = if (TIM_DATA_FILE.exists()) {
        TIM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(timDataCols.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
}

/**
 * The [DebouncedFileWriter] for the [timData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val timDataWriter = DebouncedFileWriter<AnyFrame>(TIM_DATA_FILE) { it.toCsv() }
