package io.files

import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import io.TIM_DATA_FILE
import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
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
 * The keys are the [ColumnAccessor]s and the values are the default values for the columns.
 */
val timDataCols = mutableMapOf<ColumnAccessor<Any?>, Any?>(
    match to 0,
    alliance to "blue",
    team to 0,
    column<Boolean?>("Played Defense") to false,
    column<Int?>("Defense Rating") to 0,
    column<Boolean?>("Shooting Hub") to false,
    column<Int?>("Time Left to Climb") to 0,
    column<String>("Notes") to ""
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
        dataFrameOf(timDataCols.keys.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
}

/**
 * Adds rows for any missing teams in matches to the TIM data. The rows are appended with the default values.
 */
fun populateTimData() {
    // Loop over teams and matches in the match schedule
    for ((matchNum, matchObj) in matchSchedule!!) {
        for (teamObj in matchObj.teams) {
            // See if the corresponding row exists yet
            if (timData!!.firstOrNull {
                matchNum == match().toString() && teamObj.number == team() && teamObj.color == alliance()
            } == null
            ) {
                // Get the default values for the row
                val defaults = timDataCols.map { (accessor, default) ->
                    when (accessor) {
                        match -> matchNum.toInt()
                        team -> teamObj.number
                        alliance -> teamObj.color
                        else -> default
                    }
                }
                // Create a new dataframe with one row containing the default values
                val df = dataFrameOf(
                    timDataCols.keys.mapIndexed { i, it ->
                        columnOf(values = arrayOf(defaults[i])) named it.name()
                    }
                )
                // Concat the new dataframe
                timData = timData!!.concat(df)
            }
        }
    }
}

/**
 * The [DebouncedFileWriter] for the [timData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val timDataWriter = DebouncedFileWriter<AnyFrame>(TIM_DATA_FILE) { it.toCsv() }
