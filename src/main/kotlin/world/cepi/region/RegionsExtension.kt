package world.cepi.region

import net.minestom.server.extensions.Extension

class RegionsExtension : Extension() {

    override fun initialize() {
        logger.info("[RegionsExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[RegionsExtension] has been disabled!")
    }

}