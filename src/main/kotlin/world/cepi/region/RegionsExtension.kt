package world.cepi.region

import net.minestom.server.extensions.Extension
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.region.api.RegionProvider
import world.cepi.region.command.RegionCommand
import world.cepi.region.event.PlayerRegionHandler
import java.io.File
import java.nio.file.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.createFile
import kotlin.io.path.exists

class RegionsExtension : Extension() {

    override fun initialize() {
        RegionCommand.register()

        RegionProvider.loadFromFile(regionsFile)

        with(eventNode) {
            listenOnly(PlayerRegionHandler::register)
        }

        logger.info("[RegionsExtension] has been enabled!")
    }

    override fun terminate() {
        RegionCommand.unregister()

        RegionProvider.saveToFile(regionsFile)

        logger.info("[RegionsExtension] has been disabled!")
    }

    companion object {
        val dataDir: Path
            get() {
                val dir = Path.of("./extensions/RegionsExtension")
                if (!dir.exists()) dir.createDirectories()
                return dir
            }

        val regionsFile: Path
            get() {
                val dir = dataDir.resolve("regions.json")
                if (!dir.exists()) dir.createFile()
                return dir
            }
    }

}