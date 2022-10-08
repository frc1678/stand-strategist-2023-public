package files

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

val DEBOUNCE = 1000.milliseconds

class DebouncedFileWriter<T>(private val file: File, private val deserializer: (T) -> String) {

    suspend fun start() {
        for (data in channel) withContext(Dispatchers.IO) {
            if (!file.exists()) file.createNewFile()
            file.writeText(deserializer(data))
            delay(DEBOUNCE)
        }
    }

    private val channel = Channel<T>(Channel.CONFLATED)

    suspend fun writeData(data: T) {
        channel.send(data)
    }
}
