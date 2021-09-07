package world.cepi.region.command.subcommand

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent
import net.kyori.adventure.text.event.HoverEvent
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextColor
import net.minestom.server.command.builder.Command
import net.minestom.server.command.builder.arguments.ArgumentType
import net.minestom.server.coordinate.Vec
import net.minestom.server.entity.Player
import net.minestom.server.utils.location.RelativeVec
import world.cepi.kepi.messages.sendFormattedTranslatableMessage
import world.cepi.kstom.command.addSyntax
import world.cepi.kstom.command.arguments.literal
import world.cepi.region.Selection
import world.cepi.region.command.*
import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.*

object SelectionsSubcommand : Command("selections") {

    val numberFormat = DecimalFormat("0.#")

    val index = ArgumentType.Integer("index").min(0)
    val position = ArgumentType.RelativeBlockPosition("position")
        .setDefaultValue { RelativeVec(Vec.ZERO, RelativeVec.CoordinateType.RELATIVE, true, true, true) }

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

            Selection.pos1(player, context[position].from(player))

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
                sender.sendFormattedTranslatableMessage("regions", "selection.none")
            } else {
                val region = context.get(RegionCommand.existingRegion)

                region.addSelection(Selection.selections[player.uuid]!!)
                Selection.selections.remove(player.uuid)

                sender.sendFormattedTranslatableMessage("regions", "selection.add")
            }
        }

        addSyntax(RegionCommand.existingRegion, remove, index) {
            val region = context.get(RegionCommand.existingRegion)
            val index = context.get(index)

            if (index >= 0 && index < region.selections.size) {
                region.removeSelection(region.selections[index])
                sender.sendFormattedTranslatableMessage("regions", "selection.remove")
            } else {
                sender.sendFormattedTranslatableMessage("regions", "selection.none")
            }
        }

        addSyntax(RegionCommand.existingRegion, RegionCommand.list) {
            val region = context.get(RegionCommand.existingRegion)

            if (region.selections.isEmpty()) {
                sender.sendMessage("No selections found")
                return@addSyntax
            }

            region.selections.forEachIndexed { index, selection ->

                val pos1 = arrayOf(
                    selection.pos1.x(),
                    selection.pos1.y(),
                    selection.pos1.z()
                )

                val pos1String = pos1.joinToString(", ") { numberFormat.format(it) }

                val pos2 = arrayOf(
                    selection.pos2.x(),
                    selection.pos2.y(),
                    selection.pos2.z()
                )

                val pos2String = pos2.joinToString(", ") { numberFormat.format(it) }

                sender.sendMessage(
                    Component.text(index + 1, NamedTextColor.GRAY)
                        .append(Component.text(": ", NamedTextColor.GRAY))
                        .append(Component.text("($pos1String)", TextColor.color(94, 173, 101))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to teleport!")))
                            .clickEvent(ClickEvent.runCommand("/tp ${pos1.joinToString(" ")}")))
                        .append(Component.text(", ", NamedTextColor.GRAY))
                        .append(Component.text("($pos2String)", TextColor.color(94, 173, 170))
                            .clickEvent(ClickEvent.runCommand("/tp ${pos2.joinToString(" ")}")))
                            .hoverEvent(HoverEvent.showText(Component.text("Click to teleport!")))
                )
            }
        }
    }
}