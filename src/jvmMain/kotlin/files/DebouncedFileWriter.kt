package files

import androidx.compose.runtime.LaunchedEffect
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

/**
 * The debouncing duration for file writes.
 */
val DEBOUNCE = 1000.milliseconds

/**
 * A class for managing file writing operations with debouncing.
 *
 * Debouncing ensures that there is a delay between file writes,
 * so that not too many will happen in a short period of time.
 *
 * Instances of this class should have:
 * - the [start] method called from the [LaunchedEffect] of [Observer], and
 * - the [writeData] method called whenever the corresponding data is updated (use [Observer]).
 *
 * @param file The [File] to write to.
 * @param deserializer A lambda function that takes the serialized data as a parameter
 * and returns a `String` version ready to be written to the [file].
 * @see Observer
 */
class DebouncedFileWriter<T>(private val file: File, private val deserializer: (T) -> String) {

    /**
     * Starts listening for new data and writes the new data to the [file].
     * Applies debouncing when the data are updated quickly.
     */
    suspend fun start() {
        for (data in channel) withContext(Dispatchers.IO) {
            if (!file.exists()) file.createNewFile()
            file.writeText(deserializer(data))
            delay(DEBOUNCE)
        }
    }

    /**
     * The [Channel] that all the data are sent through to the debouncing coroutine.
     */
    private val channel = Channel<T>(Channel.CONFLATED)

    /**
     * Writes the given [data] to the file. This won't write immediately if there is debouncing happening.
     */
    suspend fun writeData(data: T) {
        channel.send(data)
    }
}
