package world.cepi.region

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension
import world.cepi.region.command.RegionCommand

class RegionsExtension : Extension() {

    override fun initialize() {

        MinecraftServer.getCommandManager().register(RegionCommand)

        logger.info("[RegionsExtension] has been enabled!")
    }

    override fun terminate() {
        logger.info("[RegionsExtension] has been disabled!")
    }

}