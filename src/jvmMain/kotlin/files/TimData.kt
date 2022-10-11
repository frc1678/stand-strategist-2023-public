package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.TIM_DATA_FILE
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv
import util.emptyCol

/**
 * The main object storing all the Team-In-Match data.
 *
 * Don't directly make edits to this object, use [editTimData].
 */
var timData: AnyFrame? by mutableStateOf(DataFrame.empty())

fun readTimData() {
    timData = if (TIM_DATA_FILE.exists()) {
        TIM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(
            emptyCol<Int>("matchNumber"),
            emptyCol<String>("alliance"),
            emptyCol<Int>("teamNumber"),
            emptyCol<Boolean?>("defense"),
            emptyCol<Int?>("defenseRating"),
            emptyCol<Boolean?>("shootingHub"),
            emptyCol<Int?>("timeLeftToClimb"),
            emptyCol<String>("notes")
        )
    }
}

fun editTimData(edit: AnyFrame.() -> Unit) {
    timData = dataFrameOf(timData!!.columns()).apply(edit)
}

val timDataWriter = DebouncedFileWriter<AnyFrame>(TIM_DATA_FILE) { it.toCsv() }
