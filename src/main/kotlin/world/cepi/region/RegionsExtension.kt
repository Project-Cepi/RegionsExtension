package world.cepi.region

import net.kyori.adventure.bossbar.BossBar
import net.kyori.adventure.text.Component
import net.minestom.server.data.DataImpl
import net.minestom.server.extensions.Extension
import world.cepi.kstom.event.listenOnly
import world.cepi.kstom.command.register
import world.cepi.kstom.command.unregister
import world.cepi.region.api.RegionProvider
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

                if (player.data == null) player.data = DataImpl()

                player.data?.get<BossBar>("regions-bossbar")?.let {
                    player.hideBossBar(it)
                }

                val bossBar = BossBar.bossBar(
                    newRegion?.let { Component.text("Currently in ${it.name}") }
                        ?: Component.text("Currently in no region"),
                    0f,
                    BossBar.Color.PINK,
                    BossBar.Overlay.PROGRESS
                )

                player.showBossBar(
                    bossBar
                )

                player.data?.set("regions-bossbar", bossBar)
            }
        }



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