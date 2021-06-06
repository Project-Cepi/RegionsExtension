package world.cepi.region.serialization

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import net.minestom.server.MinecraftServer
import world.cepi.kstom.Manager
import world.cepi.region.Selection
import world.cepi.region.api.Region
import java.util.*

@ExperimentalSerializationApi
@Serializable
@SerialName("Region")
private class RegionSurrogate(
    val name: String,
    val selections: List<Selection>
)

@ExperimentalSerializationApi
object RegionSerializer : KSerializer<Region> {
    override val descriptor: SerialDescriptor = RegionSurrogate.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Region) {
        val surrogate = RegionSurrogate(value.name, value.selections)
        encoder.encodeSerializableValue(RegionSurrogate.serializer(), surrogate)
    }

    override fun deserialize(decoder: Decoder): Region {
        val surrogate = decoder.decodeSerializableValue(RegionSurrogate.serializer())
        return Region(surrogate.name, Manager.instance.instances.first()).apply {
            surrogate.selections.forEach { addSelection(it) }
        }
    }

}