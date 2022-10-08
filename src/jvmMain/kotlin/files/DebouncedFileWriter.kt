package files

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import java.io.File
import kotlin.time.Duration.Companion.milliseconds

val DEBOUNCE = 1000.milliseconds

class DebouncedFileWriter<T>(private val file: File, private val deserializer: (T) -> String) {

    suspend fun start() {
        while (true) {
            val data = channel.receive()
            file.writeText(deserializer(data))
            delay(DEBOUNCE)
        }
    }

    private val channel = Channel<T>(Channel.CONFLATED)

    fun writeData(data: T) {
        channel.trySend(data)
    }
}
