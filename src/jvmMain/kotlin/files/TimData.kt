package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.TIM_DATA_FILE
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.named
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv

private val timDataCols = mutableListOf<ColumnAccessor<Any?>>()

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

fun readTimData() {
    timData = if (TIM_DATA_FILE.exists()) {
        TIM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(timDataCols.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
    doneReadingTimData = true
}

var doneReadingTimData = false

fun editTimData(edit: AnyFrame.() -> Unit) {
    timData = dataFrameOf(timData!!.columns()).apply(edit)
}

val timDataWriter = DebouncedFileWriter<AnyFrame>(TIM_DATA_FILE) { it.toCsv() }
