package world.cepi.region

import net.minestom.server.event.player.PlayerSpawnEvent
import net.minestom.server.extensions.Extension
import world.cepi.kstom.Manager
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.region.api.*
import world.cepi.region.command.RegionCommand
import world.cepi.region.event.PlayerRegionHandler
import world.cepi.region.event.PlayerRegionUpdateEvent
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists

class RegionsExtension : Extension() {

    override fun initialize() {
        RegionCommand.register()

        RegionProvider.loadFromPath(regionsFile)

        with(eventNode) {
            listenOnly(PlayerRegionHandler::register)
            listenOnly<PlayerRegionUpdateEvent> {
                oldRegion?.bossBar?.let { Manager.bossBar.removeBossBar(player, it) }
                player.refreshRegionBossBar()
            }
            listenOnly<PlayerSpawnEvent> {
                player.refreshRegionBossBar()
            }
        }

        eventNode.addChild(Region.noPassnode)

        logger.info("[RegionsExtension] has been enabled!")
    }

    override fun terminate() {
        RegionCommand.unregister()

        RegionProvider.saveToPath(regionsFile)

        logger.info("[RegionsExtension] has been disabled!")
    }

    companion object {
        val dataDir by lazy {
            val dir = Path.of("./extensions/RegionsExtension")
            if (!dir.exists()) dir.createDirectories()
            dir
        }

        val regionsFile by lazy {
            val dir = dataDir.resolve("regions.json")
            if (!dir.exists()) dir.createFile()
            dir
        }
    }

}