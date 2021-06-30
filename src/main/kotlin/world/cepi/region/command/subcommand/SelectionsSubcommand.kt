package world.cepi.region.command.subcommand

import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.utils.BlockPosition
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.region.Selection
import world.cepi.region.command.*
import java.util.*

object SelectionsSubcommand : Command("selections") {
    val selections: Map<UUID, Selection> = mutableMapOf()

    val index = ArgumentType.Integer("index")
    val position = ArgumentType.RelativeBlockPosition("position")

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
        addSyntax(pos1, position) {
            selections as MutableMap
            val player = sender as Player

            val selection = getOrCreateSelection(player)
            selection.pos1 = context.get(position).from(player)
        }

        addSyntax(pos2, position) {
            selections as MutableMap
            val player = sender as Player
            
            val selection = getOrCreateSelection(player)
            selection.pos2 = context.get(position).from(player)
        }

        addSyntax(RegionCommand.existingRegion, add) {
            selections as MutableMap
            val player = sender as Player

            if (!selections.contains(player.uuid)) {
                sender.sendMessage(selectionDoesNotExist)
            } else {
                val region = context.get(RegionCommand.existingRegion)

                region.addSelection(selections[player.uuid]!!)
                selections.remove(player.uuid)

                sender.sendMessage(selectionAdded)
            }
        }

        addSyntax(RegionCommand.existingRegion, remove, index) {
            val player = sender as Player

            val region = context.get(RegionCommand.existingRegion)
            val index = context.get(index)

            if (index >= 0 && index < region.selections.size) {
                region.removeSelection(region.selections[index])
                sender.sendMessage(selectionRemoved)
            } else {
                sender.sendMessage(selectionDoesNotExist)
            }
        }

        addSyntax(RegionCommand.existingRegion, RegionCommand.list) {

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