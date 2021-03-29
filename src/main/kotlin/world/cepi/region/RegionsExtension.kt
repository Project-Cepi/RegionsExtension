package world.cepi.region

import net.minestom.server.extensions.Extension
import world.cepi.region.cepiregions.implementation.v1_0fw.CepiRegionProvider

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

        /**
         * Gets the [RegionProvider] implementation that
         * supports the desired given version. The returned
         * implementation might actually be newer than the
         * exact given version, but this is done only when
         * the newer version directly supports the older one.
         * (i.e. old functions have the same behaviour) In
         * other cases legacy support magic is engaged.
         *
         * @param version The desired version
         *
         * @return [RegionProvider] implementation for that
         * version.
         *
         * @throws UnsupportedOperationException If the given
         * version is not supported at all.
         */
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