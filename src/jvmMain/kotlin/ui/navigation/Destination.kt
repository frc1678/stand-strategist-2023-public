package ui.navigation

import androidx.compose.runtime.Composable
import io.files.Settings
import io.files.editSettings
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

@Serializable(with = DestinationSerializer::class)
class Destination(
    val content: @Composable () -> Unit,
    val name: String,
    val back: () -> NavGraph,
    val next: () -> NavGraph? = { null },
    val onBack: Settings.() -> Unit = {},
    val onNext: Settings.() -> Unit = {}
)

object DestinationSerializer : KSerializer<Destination> {
    override val descriptor = PrimitiveSerialDescriptor("Destination", PrimitiveKind.STRING)
    override fun deserialize(decoder: Decoder) = enumValueOf<NavGraph>(decoder.decodeString()).destination
    override fun serialize(encoder: Encoder, value: Destination) = encoder.encodeString(
        enumValues<NavGraph>().firstOrNull { it.destination === value }?.name ?: NavGraph.STARTING.toString()
    )
}

fun navigateTo(destination: Destination) = editSettings { screen = destination }

fun navigateTo(otherScreen: NavGraph) = editSettings { screen = otherScreen.destination }
