package world.cepi.region

import net.minestom.server.MinecraftServer
import net.minestom.server.extensions.Extension
import world.cepi.region.api.RegionProvider
import world.cepi.region.command.RegionCommand
import java.io.File

class RegionsExtension : Extension() {

    override fun initialize() {
        MinecraftServer.getCommandManager().register(RegionCommand)

        RegionProvider.loadFromFile(regionsFile)

        logger.info("[RegionsExtension] has been enabled!")
    }

    override fun terminate() {
        RegionProvider.saveToFile(regionsFile)

        logger.info("[RegionsExtension] has been disabled!")
    }

    companion object {
        val dataDir: File
            get() {
                val dir = File("./extensions/RegionsExtension")
                if(!dir.exists()) dir.mkdirs()
                return dir
            }

        val regionsFile: File
            get() {
                val file = File(dataDir, "regions.json")
                if(!file.exists()) file.createNewFile()
                return file
            }
    }

}