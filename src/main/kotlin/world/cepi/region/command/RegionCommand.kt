package world.cepi.region.command

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.minestom.server.MinecraftServer
import net.minestom.server.command.CommandSender
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.event.player.PlayerDisconnectEvent
import net.minestom.server.instance.Instance
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kepi.subcommands.Help
import world.cepi.kstom.Manager
import world.cepi.kstom.addEventCallback
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.setArgumentCallback
import world.cepi.region.api.RegionProvider
import world.cepi.region.Selection
import java.util.*

object RegionCommand : Command("region") {

    private val selectedPositions = HashMap<CommandSender, Selection>()

    init {
        Manager.globalEvent.addEventCallback<PlayerDisconnectEvent> {
            selectedPositions.remove(player)
        }

        val create = "create".literal()
        val delete = "delete".literal()

        val pos1 = "pos1".literal()
        val pos2 = "pos2".literal()

        val selections = "selections".literal()
        val add = "add".literal()
        val remove = "remove".literal()

        val list = "list".literal()

        val show = "show".literal()

        val regionName = ArgumentType.DynamicWord("regionName")
            .fromRestrictions { RegionProvider.regions.containsKey(it) }

        setArgumentCallback(regionName) { sender, exception ->
            sender.sendFormattedMessage(regionDoesNotExist, Component.text(exception.input, NamedTextColor.BLUE))
        }

        val newRegionName = ArgumentType.String("newRegionName")
        // Find some instance as a default value.
        // The default value would be rarely used as if a command is sent by a player then the instance in which the
        // player is in will be used, and if it is sent by a console then the UUID should be specified.
        val worldId = ArgumentType.UUID("worldId").setDefaultValue{ MinecraftServer.getInstanceManager().instances.firstOrNull()?.uniqueId }


        addSubcommand(Help(
            Component.text("/$name create <region name> [<world uuid>]"),
                Component.text(" Creates a new region."),
                Component.text("/$name delete <region name>"),
                Component.text(" Deletes a region."),
                Component.text("/$name pos1 [<coordinates>]"),
                Component.text(" Sets/gets the first position for making a selection."),
                Component.text("/$name pos2 [<coordinates>]"),
                Component.text(" Sets/gets the second position for making a selection."),
                Component.text("/$name selections <region name> add <region name> [<world uuid>]"),
                Component.text(" Adds the selected blocks to a given region."),
                Component.text("/$name selections <region name> remove <index> [<world uuid>]"),
                Component.text(" Removes a selection from the region."),
                Component.text("/$name selections <region name> list [<pool name>]"),
                Component.text(" Lists all the selections in a given region."),
                Component.text("/$name list"),
                Component.text(" Lists all of the registered regions."),
                Component.text("/$name show <region name>"),
                Component.text(" Visually show a given region."),

        ))

        addSyntax(create, newRegionName, worldId) { sender, args ->
            val instance = if(sender is Player) {
                sender.instance!!
            } else {
                MinecraftServer.getInstanceManager().getInstance(args.get(worldId))!!
            }


            val region = RegionProvider.createRegion(args.get(newRegionName), instance)

            sender.sendFormattedMessage(
                regionCreated,
                Component.text(region.name)
            )
        }

        addSyntax(delete, regionName) { sender, args ->
            val region = args.get(regionName)

            RegionProvider.remove(region)

            sender.sendFormattedMessage(
                regionDeleted,
                Component.text(region)
            )
        }

        addSyntax(pos1) { sender ->

        }

        addSyntax(pos2) { sender ->

        }

        addSyntax(selections, regionName, add) { sender, args ->

        }

        addSyntax(selections, regionName, remove) { sender, args ->

        }

        addSyntax(selections, regionName, list) { sender, args ->

        }

        addSyntax(list) { sender ->
            val regions = RegionProvider.regions.values

            sender.sendFormattedMessage(regionsList, Component.text(regions.joinToString { it.name }))
        }

        addSyntax(show) { sender ->

        }

    }
}