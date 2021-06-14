package world.cepi.region.command.subcommand

import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.utils.BlockPosition
import world.cepi.kepi.messages.sendFormattedMessage
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.region.Selection
import world.cepi.region.command.*
import java.util.*

object SelectionsSubcommand : Command("selections") {
    val selections: Map<UUID, Selection> = mutableMapOf()

    val index = ArgumentType.Integer("index")
    val x = ArgumentType.Integer("x")
    val y = ArgumentType.Integer("y")
    val z = ArgumentType.Integer("z")

    val add = "add".literal()
    val remove = "remove".literal()
    val pos1 = "pos1".literal()
    val pos2 = "pos2".literal()

    init {
        setCondition { sender, _ ->
            if (!sender.isPlayer) {
                sender.sendFormattedTranslatableMessage("common", "command.only_players")
                return@setCondition false
            } else return@setCondition true
        }

        // TODO: Translations
        addSyntax(pos1, x, y, z) { sender, args ->
            selections as MutableMap
            sender as Player

            val selection = getOrCreateSelection(sender)
            selection.pos1 = BlockPosition(args.get(x), args.get(y), args.get(z))
        }

        addSyntax(pos2, x, y, z) { sender, args ->
            selections as MutableMap
            sender as Player
            
            val selection = getOrCreateSelection(sender)
            selection.pos2 = BlockPosition(args.get(x), args.get(y), args.get(z))
        }

        addSyntax(RegionCommand.existingRegion, add) { sender, args ->
            selections as MutableMap
            sender as Player

            if(!selections.contains(sender.uuid)) {
                sender.sendMessage(selectionDoesNotExist)
            } else {
                val region = args.get(RegionCommand.existingRegion)

                region.addSelection(selections[sender.uuid]!!)
                selections.remove(sender.uuid)

                sender.sendMessage(selectionAdded)
            }
        }

        addSyntax(RegionCommand.existingRegion, remove, index) { sender, args ->
            sender as Player

            val region = args.get(RegionCommand.existingRegion)
            val index = args.get(index)

            if(index >= 0 && index < region.selections.size) {
                region.removeSelection(region.selections[index])
                sender.sendMessage(selectionRemoved)
            } else {
                sender.sendMessage(selectionDoesNotExist)
            }
        }

        addSyntax(RegionCommand.existingRegion, RegionCommand.list) { sender, args ->

        }
    }

    private fun getOrCreateSelection(player: Player): Selection {
        selections as MutableMap

        return if(selections.contains(player.uuid)) {
            selections[player.uuid]!!
        } else {
            val selection = Selection(BlockPosition(0, 0, 0), BlockPosition(0, 0, 0)) // Placeholder positions
            selections[player.uuid] = selection
            selection
        }
    }
}