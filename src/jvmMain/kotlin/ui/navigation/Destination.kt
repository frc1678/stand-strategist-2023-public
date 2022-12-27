package ui.navigation

import androidx.compose.runtime.Composable
import io.files.Settings
import io.files.editSettings
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

/**
 * Class used to represent a page that can be navigated to or from.
 * You should only create instances of this class in the [NavGraph],
 * so that the current destination can be properly saved to the settings file.
 *
 * @property content A [Composable] function containing the content to be displayed when this destination is the current
 * destination.
 * @property name A human-readable name for this destination.
 * @property back A function returning the screen from [NavGraph] that should be navigated to when the 'Back' navigation
 * button is clicked from this destination. Note that this is a function, so you need to call it as a function in order
 * to retrieve the [NavGraph] object.
 * @property next A function returning the screen from [NavGraph] that should be navigated to when the 'Next' navigation
 * button is clicked from this destination. If the returned value of this function is `null`, this indicates that there
 * is no specific next destination and the user should not have the option to go to the next destination. Note that this
 * is a function, so you need to call it as a function in order to retrieve the [NavGraph] object.
 * @property onBack A callback that is invoked when the user clicks the 'Back' navigation button. This is executed
 * before the screen is changed.
 * @property onNext A callback that is invoked when the user clicks the 'Next' navigation button. This is executed
 * before the screen is changed.
 */
@Serializable(with = DestinationSerializer::class)
class Destination(
    val content: @Composable () -> Unit,
    val name: String,
    val back: () -> NavGraph,
    val next: () -> NavGraph? = { null },
    val onBack: Settings.() -> Unit = {},
    val onNext: Settings.() -> Unit = {}
)

/**
 * Serializer object allowing serialization and deserialization of [Destination] objects. The destination is stored in
 * the data by the name of its enum constant in [NavGraph].
 */
object DestinationSerializer : KSerializer<Destination> {
    /**
     * [SerialDescriptor] used to specify that [Destination] objects are to be serialized as strings.
     */
    override val descriptor = PrimitiveSerialDescriptor("Destination", PrimitiveKind.STRING)

    /**
     * Looks for an enum constant in [NavGraph] with a name matching the decoded string, and returns that [Destination]
     * as the deserialized object.
     */
    override fun deserialize(decoder: Decoder) = enumValueOf<NavGraph>(decoder.decodeString()).destination

    /**
     * Searches for a [Destination] in [NavGraph] that matches the given [value] and encodes the [Destination] object
     * using the name of its enum constant.
     */
    override fun serialize(encoder: Encoder, value: Destination) = encoder.encodeString(
        enumValues<NavGraph>().firstOrNull { it.destination === value }?.name ?: NavGraph.STARTING.toString()
    )
}

/**
 * Helper function that sets the current screen to the given [otherScreen].
 */
fun navigateTo(otherScreen: NavGraph) = editSettings { screen = otherScreen.destination }
