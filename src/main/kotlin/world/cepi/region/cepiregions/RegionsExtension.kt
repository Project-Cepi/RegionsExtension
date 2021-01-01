package world.cepi.region.cepiregions

import net.minestom.server.extensions.Extension;
import org.jetbrains.annotations.NotNull
import world.cepi.region.RegionProvider

class RegionsExtension : Extension() {

    override fun initialize() {
        provider = CepiRegionProvider()

        logger.info("[RegionsExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[RegionsExtension] has been disabled!")
    }

    companion object {

        private lateinit var provider: CepiRegionProvider

        @NotNull
        fun getProvider(version: String): RegionProvider {

            when (version) {

                "1.0" -> return provider
                // New API versions here.
                // If the API changes, but it still supports old
                // versions of itself, then the old versions can
                // safely return the new API.

                else -> throw UnsupportedOperationException(
                    "Version $version of Regions API is not supported by this implementation"
                )

            }

        }

    }

}