package io.files

/**
 * The header for the Team column in team/TIM data.
 */
const val team = "Team"

/**
 * The header for the Match Number column in the TIM data.
 */
const val match = "Match Number"

/**
 * The header for the Alliance column in the TIM data.
 */
const val alliance = "Alliance"

/**
 * Columns that are shown in the Team Notes page.
 */
val teamDataCols = mapOf<Column, Any>(
    team(DataType.Str) to "0",
    "Auto Strategies"(DataType.Str) to "",
    "Driving and Scoring Competence"(DataType.Str) to "",
    "Strengths/Weaknesses"(DataType.Str) to "",
    "Notes"(DataType.Str) to ""
)

/**
 * Columns that are shown in the Match Data page.
 */
val timDataCols = mapOf<Column, Any>(
    match(DataType.Num) to "",
    alliance(DataType.Str) to "blue",
    team(DataType.Str) to "0",
    "Played Defense"(DataType.Bool) to false,
    "Defense Rating"(DataType.Num) to 0,
    "Drives over charging station?"(DataType.Bool) to false,
    "Moves pieces between rows?"(DataType.Bool) to false,
    "Notes"(DataType.Str) to ""
)

/**
 * Data type for a column.
 */
sealed interface DataType {
    /**
     * [DataType] for textual data.
     */
    object Str : DataType

    /**
     * [DataType] for integer data.
     */
    object Num : DataType

    /**
     * [DataType] for boolean data.
     */
    object Bool : DataType
}

/**
 * Information about a given column in the data.
 */
data class Column(val name: String, val type: DataType)

/**
 * Helper function to create a [Column] object.
 */
operator fun String.invoke(dataType: DataType) = Column(this, dataType)
