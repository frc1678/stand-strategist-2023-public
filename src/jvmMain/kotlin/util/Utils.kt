package util

import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.named

inline fun <reified T> emptyCol(named: String) = columnOf<T>(values = emptyArray()) named named
