package world.cepi.region.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.event.player.PlayerDisconnectEvent
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kepi.subcommands.Help
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.setArgumentCallback
import world.cepi.region.api.RegionProvider
import world.cepi.region.Selection
import world.cepi.region.api.Region
import java.util.*

object RegionCommand : Command("region") {

    private val selectedPositions = HashMap<CommandSender, Selection>()

    init {
        MinecraftServer.getGlobalEventHandler().addEventCallback(PlayerDisconnectEvent::class.java) {
            selectedPositions.remove(it.player)
        }

        val create = "create".literal()
        val delete = "delete".literal()

        val pos1 = "pos1".literal()
        val pos2 = "pos2".literal()

        val blocks = "blocks".literal()
        val add = "add".literal()
        val remove = "remove".literal()

        val list = "list".literal()

        val show = "show".literal()

        val pool = "pool".literal()

        val poolName = ArgumentType.DynamicWord("poolName")
            .fromRestrictions { RegionProvider.pools.any { pool -> pool.name == it } }
            .map {
                RegionProvider[it]!!
            }

        setArgumentCallback(poolName) { sender, exception ->
            sender.sendFormattedMessage(regionPoolNotExist, Component.text(exception.input, NamedTextColor.BLUE))
        }

        val regionName = ArgumentType.DynamicWord("regionName")
            .fromRestrictions { input -> RegionProvider.pools.map { it.regions }.flatten().any { it.name == input } }

        val newRegionName = ArgumentType.String("newRegionName")


        addSubcommand(Help(
            Component.text("/$name create <pool name> <region name>"),
                Component.text(" Creates a new region in a given regionpool."),
                Component.text("/$name delete <pool name> <region name>"),
                Component.text(" Deletes a region in a given regionpool."),
                Component.text("/$name pos1 [<coordinates>]"),
                Component.text(" Sets/gets the first position for making a selection."),
                Component.text("/$name pos2 [<coordinates>]"),
                Component.text(" Sets/gets the second position for making a selection."),
                Component.text("/$name addblocks <pool name> <region name> [<world uuid>]"),
                Component.text(" Adds the selected blocks to the region in the given regionpool"),
                Component.text("/$name removeblocks <pool name> <region name> [<world uuid>]"),
                Component.text(" Removes the selected blocks from the region in"),
                Component.text(" the given regionpool."),
                Component.text("/$name list [<pool name>]"),
                Component.text(" Lists all the regions in the given regionpool,"),
                Component.text(" or all the pools if argument omitted."),
                Component.text("/$name show <pool name> <region name>"),
                Component.text(" Visually show the region in the given regionpool."),
                Component.text("/$name createpool <pool name>"),
                Component.text(" Creates a new regionpool."),
                Component.text("/$name deletepool <pool name>"),
                Component.text(" Deletes a regionpool."),

        ))

        addSyntax(create, poolName, newRegionName) { sender, args ->
            val poolObject = args.get(poolName)

            val region = poolObject.createRegion(args.get(newRegionName))

            sender.sendFormattedMessage(
                regionCreated,
                Component.text(poolObject.name),
                Component.text(region.name)
            )
        }

        addSyntax(delete, poolName, regionName) { sender, args ->
            val poolObject = args.get(poolName)

            val region = poolObject[args.get(newRegionName)]!!

            poolObject.remove(region)

            sender.sendFormattedMessage(
                regionDeleted,
                Component.text(poolObject.name),
                Component.text(region.name)
            )
        }

        addSyntax(pos1) { sender ->

        }

        addSyntax(pos2) { sender ->

        }

        addSyntax(blocks, add) { sender, args ->

        }

        addSyntax(blocks, remove) { sender, args ->

        }

        addSyntax(pool, create) { sender, args ->

        }

        addSyntax(pool, delete) { sender, args ->

        }

        addSyntax(list) { sender ->
            val pools = RegionProvider.pools

            pools.joinToString { it.name }

            sender.sendFormattedMessage(regionPoolsList, Component.text(pools.joinToString { it.name }))
        }

        addSyntax(show) { sender ->

        }

    }
}