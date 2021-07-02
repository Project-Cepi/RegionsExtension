package world.cepi.region.command.subcommand

import net.kyori.adventure.text.Component
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.entity.Player
import net.minestom.server.utils.BlockPosition
import net.minestom.server.utils.location.RelativeBlockPosition
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.region.Selection
import world.cepi.region.command.*
import java.util.*

object SelectionsSubcommand : Command("selections") {
    val index = ArgumentType.Integer("index").min(0)
    val position = ArgumentType.RelativeBlockPosition("position")
        .setDefaultValue { RelativeBlockPosition(BlockPosition(0, 0, 0), true, true, true) }

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
            val player = sender as Player

            Selection.pos2(player, context[position].from(player))

            player.sendMessage("Set position 1!")
        }

        addSyntax(pos2, position) {
            val player = sender as Player
            
            Selection.pos2(player, context[position].from(player))

            player.sendMessage("Set position 2!")
        }

        addSyntax(RegionCommand.existingRegion, add) {
            Selection.selections as MutableMap
            val player = sender as Player

            if (!Selection.selections.contains(player.uuid)) {
                sender.sendMessage(selectionDoesNotExist)
            } else {
                val region = context.get(RegionCommand.existingRegion)

                region.addSelection(Selection.selections[player.uuid]!!)
                Selection.selections.remove(player.uuid)

                sender.sendMessage(selectionAdded)
            }
        }

        addSyntax(RegionCommand.existingRegion, remove, index) {
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
            val region = context.get(RegionCommand.existingRegion)

            region.selections.forEachIndexed { index, selection ->
                sender.sendMessage(
                    Component.text(index)
                        .append(Component.text(": "))
                        .append(Component.text(selection.pos1.toString()))
                        .append(Component.text(selection.pos2.toString()))
                )
            }
        }
    }
}