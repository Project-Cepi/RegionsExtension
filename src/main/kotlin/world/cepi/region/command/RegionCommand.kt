package world.cepi.region.command

import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Player
import world.cepi.kepi.command.subcommand.applyHelp
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kstom.Manager
import world.cepi.kstom.command.addSubcommands
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.setArgumentCallback
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.suggest
import world.cepi.region.api.RegionProvider
import world.cepi.region.command.subcommand.*

object RegionCommand : Command("region") {
    val regionName = ArgumentType.Word("name").map { name ->
        val region = RegionProvider[name]
        if(region != null) throw ArgumentSyntaxException("Region exists", name, 1)
        name
    }

    val existingRegion = ArgumentType.Word("region").map { name ->
        RegionProvider[name]?: throw ArgumentSyntaxException("Invalid region", name, 1)
    }.suggest {
        RegionProvider.regions.values
            .map { it.name }
    }
    // Find some instance as a default value.
    // The default value would be rarely used as if a command is sent by a player then the instance in which the
    // player is in will be used, and if it is sent by a console then the UUID should be specified.
    val world = ArgumentType.UUID("world").map { world ->
        Manager.instance.getInstance(world)?: throw ArgumentSyntaxException("Invalid world", world.toString(), 1)
    }.setDefaultValue { MinecraftServer.getInstanceManager().instances.firstOrNull() }

    val create = "create".literal()
    val delete = "delete".literal()
    val list = "list".literal()
    val show = "show".literal()

    init {
        //TODO: Translations
        setArgumentCallback(regionName) {
            sender.sendMessage(regionAlreadyExists)
        }

        setArgumentCallback(existingRegion) {
            sender.sendMessage(regionDoesNotExist)
        }

        setArgumentCallback(world) {
            sender.sendMessage(worldDoesNotExist)
        }

        addSyntax(create, regionName, world) {
            val instance = if (sender is Player) {
                (sender as Player).instance!!
            } else {
                context.get(world)
            }

            RegionProvider.createRegion(context.get(regionName), instance)
            sender.sendMessage(regionCreated)
        }

        addSyntax(delete, existingRegion) {
            RegionProvider.remove(context.get(existingRegion).name)
            sender.sendMessage(regionDeleted)
        }

        addSyntax(list) {
            val regions = RegionProvider.regions.values
            sender.sendFormattedMessage(Component.text(regionsList), Component.text(regions.joinToString { it.name }))
        }

        addSyntax(show) {

        }

        applyHelp {
            """
                /$name create <region name> [<world uuid>]
                 Creates a new region.
                /$name delete <region name>
                 Deletes a region.
                /$name selections pos1 [<coordinates>]
                 Sets/gets the first position for making a selection.
                /$name selections pos2 [<coordinates>]
                 Sets/gets the second position for making a selection.
                /$name selections <region name> add
                 Adds the selected blocks to a given region.
                /$name selections <region name> remove <index>
                 Removes a selection from the region.
                /$name selections <region name> list
                 Lists all the selections in a given region.
                /$name list
                 Lists all of the registered regions.
                /$name show <region name>
                 Visually show a given region.
             """.trimIndent()
        }

        addSubcommands(SelectionsSubcommand, MetaSubcommand)
    }
}