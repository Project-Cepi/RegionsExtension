package world.cepi.region.command

import net.kyori.adventure.text.Component
import net.minestom.server.MinecraftServer
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.command.builder.exception.ArgumentSyntaxException
import net.minestom.server.entity.Player
import world.cepi.kepi.command.subcommand.applyHelp
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.arguments.literal
import world.cepi.kstom.command.setArgumentCallback
import world.cepi.kstom.command.addSyntax
import world.cepi.region.api.RegionProvider
import world.cepi.region.command.subcommand.SelectionsSubcommand

object RegionCommand : Command("region") {
    val regionName = ArgumentType.Word("name").map { name ->
        val region = RegionProvider[name]
        if(region != null) throw ArgumentSyntaxException("Crate exists", name, 1)
        name
    }

    val existingRegion = ArgumentType.Word("region").map { name ->
        RegionProvider[name]?: throw ArgumentSyntaxException("Invalid crate", name, 1)
    }
    // Find some instance as a default value.
    // The default value would be rarely used as if a command is sent by a player then the instance in which the
    // player is in will be used, and if it is sent by a console then the UUID should be specified.
    val world = ArgumentType.UUID("world").map { world ->
        MinecraftServer.getInstanceManager().getInstance(world)?: throw ArgumentSyntaxException("Invalid world", world.toString(), 1)
    }.setDefaultValue { MinecraftServer.getInstanceManager().instances.firstOrNull() }

    val create = "create".literal()
    val delete = "delete".literal()
    val list = "list".literal()
    val show = "show".literal()

    init {
        //TODO: Translations
        setArgumentCallback(regionName) { sender ->
            sender.sendMessage(regionAlreadyExists)
        }

        setArgumentCallback(existingRegion) { sender ->
            sender.sendMessage(regionDoesNotExist)
        }

        setArgumentCallback(world) { sender ->
            sender.sendMessage(worldDoesNotExist)
        }

        addSyntax(create, regionName, world) { sender, args ->
            val instance = if(sender is Player) {
                sender.instance!!
            } else {
                args.get(world)
            }

            RegionProvider.createRegion(args.get(regionName), instance)
            sender.sendMessage(regionCreated)
        }

        addSyntax(delete, existingRegion) { sender, args ->
            RegionProvider.remove(args.get(existingRegion).name)
            sender.sendMessage(regionDeleted)
        }

        addSyntax(list) { sender ->
            val regions = RegionProvider.regions.values
            sender.sendFormattedMessage(Component.text(regionsList), Component.text(regions.joinToString { it.name }))
        }

        addSyntax(show) { sender, args ->

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

        addSubcommand(SelectionsSubcommand)
    }
}